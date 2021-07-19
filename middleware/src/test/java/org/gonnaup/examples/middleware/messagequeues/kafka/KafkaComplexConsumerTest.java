package org.gonnaup.examples.middleware.messagequeues.kafka;

import org.junit.jupiter.api.Test;

/**
 * @author gonnaup
 * @version created at 2021/7/19 22:05
 */
class KafkaComplexConsumerTest {

    @Test
    void baseConsumer() {
        TestUtil.runWithSeparateThreadAndBothEnd(() -> SimpleKafkaClient.kafkaProducer(50, 10), () -> KafkaComplexConsumer.baseConsumer(50));
    }
}