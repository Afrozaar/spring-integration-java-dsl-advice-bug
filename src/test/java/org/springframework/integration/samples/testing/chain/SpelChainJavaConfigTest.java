package org.springframework.integration.samples.testing.chain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.messaging.Message;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

@ContextConfiguration(classes = Config.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpelChainJavaConfigTest {

    @Autowired
    private StandardIntegrationFlow flow;

    @Autowired
    @Qualifier("successChannel")
    QueueChannel successChannel;

    @Test
    public void Go() throws InterruptedException {
        flow.start();
        Message<?> receive = successChannel.receive(1000);
        System.out.println(receive);
        Assertions.assertThat(receive).isNotNull();
    }
}
