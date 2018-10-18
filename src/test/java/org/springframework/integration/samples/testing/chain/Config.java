package org.springframework.integration.samples.testing.chain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.handler.advice.ExpressionEvaluatingRequestHandlerAdvice;
import org.springframework.messaging.MessageHandler;

import org.aopalliance.aop.Advice;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@EnableIntegration
public class Config {

    /*
     *  <bridge input-channel="outputChannel"
        output-channel="testChannel"/>
        
    <channel id="testChannel">
        <queue/>
    </channel>
     */

    @Bean
    public AtomicInteger integerSource() {
        return new AtomicInteger();
    }

    @Bean
    public IntegrationFlow myFlow() {
        IntegrationFlowBuilder from = IntegrationFlows.from(integerSource()::getAndIncrement,
                                                            c -> c.poller(Pollers.fixedRate(100)));

        return from
                .channel("input-channel").get();

    }

    @Bean
    @ServiceActivator(inputChannel = "input-channel", adviceChain = "outboundFileCopyAdvice")
    public MessageHandler ftpHandler() {
        return message -> {
        };
    }

    @Bean
    public Advice outboundFileCopyAdvice() {
        ExpressionEvaluatingRequestHandlerAdvice advice = new ExpressionEvaluatingRequestHandlerAdvice();
        advice.setOnSuccessExpressionString("payload.toString()");
        advice.setSuccessChannel(successChannel());
        return advice;
    }

    @Bean
    public QueueChannel successChannel() {
        return MessageChannels.queue().get();
    }

}
