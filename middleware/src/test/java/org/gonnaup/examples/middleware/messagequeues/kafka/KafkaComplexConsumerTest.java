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

    @Test
    void assinSpecialPartitions() {
        TestUtil.runWithSeparateThreadAndBothEnd(() -> SimpleKafkaClient.kafkaProducer(10, 100), () -> KafkaComplexConsumer.assinSpecialPartitions(1));
    }

    @Test
    void manualCommitOffset() {
        TestUtil.runWithSeparateThreadAndBothEnd(() -> SimpleKafkaClient.kafkaProducer(2000, 100), () -> KafkaComplexConsumer.manualCommitOffset(2000));
    }
}