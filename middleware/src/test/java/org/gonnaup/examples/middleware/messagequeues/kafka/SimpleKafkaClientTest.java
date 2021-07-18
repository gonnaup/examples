package org.gonnaup.examples.middleware.messagequeues.kafka;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

/**
 * @author gonnaup
 * @version 2021/7/18 21:40
 */
class SimpleKafkaClientTest {

    @Test
    public void test() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);
        new Thread(() -> {
            SimpleKafkaClient.kafkaProducer();
            latch.countDown();
        }, "producer").start();
        new Thread(() -> {
            SimpleKafkaClient.kafkaConsumer();
            latch.countDown();
        }, "consumer").start();
        latch.await();
    }

}