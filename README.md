Little project to simulate and demonstrate: https://jira.spring.io/browse/INT-4545

It sets up a simple message source, message handler and then via a service activator to which then via a service activator to an advice.

The advice chain returns a string and the test listens for messages on the advice output channel.

Spring integration 5.0.7.RELEASE the test passes.

Spring Integration 5.0.8.RELEASE it fails.
