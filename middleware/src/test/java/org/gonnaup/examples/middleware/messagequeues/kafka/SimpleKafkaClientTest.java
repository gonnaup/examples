package org.gonnaup.examples.middleware.messagequeues.kafka;

import org.junit.jupiter.api.Test;

/**
 * @author gonnaup
 * @version 2021/7/18 21:40
 */
class SimpleKafkaClientTest {

    @Test
    public void test() {
        TestUtil.runWithSeparateThreadAndBothEnd(() -> SimpleKafkaClient.kafkaProducer(5, 50), SimpleKafkaClient::kafkaConsumer);
    }

}