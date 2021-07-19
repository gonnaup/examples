package org.gonnaup.examples.middleware.messagequeues.kafka;

import java.util.concurrent.CountDownLatch;

/**
 * @author gonnaup
 * @version created at 2021/7/19 21:45
 */
public class TestUtil {

    public static void runWithSeparateThreadAndBothEnd(Runnable producer, Runnable consumer) {
        CountDownLatch latch = new CountDownLatch(2);//等待两个线程都结束
        new Thread(() -> {
            producer.run();
            latch.countDown();
        }, "producer").start();
        new Thread(() -> {
            consumer.run();
            latch.countDown();
        }, "producer").start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
