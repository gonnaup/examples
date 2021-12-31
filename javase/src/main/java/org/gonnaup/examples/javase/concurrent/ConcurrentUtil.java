package org.gonnaup.examples.javase.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.javase.VirtualResource;

import java.util.concurrent.*;

import static org.gonnaup.examples.javase.CommonUtil.currentThreadName;
import static org.gonnaup.examples.javase.CommonUtil.sleep;

/**
 * @author gonnaup
 * @version created at 2021/12/30 16:02
 */
@Slf4j
public class ConcurrentUtil {

    /**
     * {@link Semaphore}: 允许同时访问资源的线程数
     */
    static void semaphore() {
        final Semaphore semaphore = new Semaphore(5);//允许同时5个线程访问资源
        final VirtualResource resource = new VirtualResource("Semaphore VR", "Semaphore Virtual Resource");
        ExecutorService threadPool = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            threadPool.submit(() -> {
                try {
                    semaphore.acquire();
                    log.info("线程 {} 访问资源 {}", currentThreadName(), resource);
                    sleep(1, TimeUnit.SECONDS);
                    semaphore.release();
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                }
            });
        }
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
            sleep(100, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * {@link CountDownLatch}: 倒计时器，在计数器调用{@link CountDownLatch#countDown()}减到0时，{@link CountDownLatch#await()}停止等待
     */
    static void countDownLatch() {
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        final VirtualResource resource = new VirtualResource("CountDownLatch VR", "CountDownLatch Virtual Resource");
        var threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            threadPool.submit(() -> {
                log.info("线程 {} 访问资源 {}", currentThreadName(), resource);
                sleep(1, TimeUnit.SECONDS);
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            threadPool.shutdown();

        }
    }

    /**
     * {@link CyclicBarrier}: 让一组线程被阻塞，知道最后这组线虫中一个线程达到阻塞点时，所有线程都被激活
     */
    static void cyclicBarrier() {
        final CyclicBarrier barrier = new CyclicBarrier(3);
        var threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 9; i++) {
            final var n = i;
            threadPool.submit(() -> {
                log.info("第 {} 线程准备。。。", n);
                try {
                    sleep(3, TimeUnit.SECONDS);
                    barrier.await();//等待，直到有3个线程准备完毕
                } catch (InterruptedException | BrokenBarrierException e) {
                    log.error(e.getMessage());
                }
                log.info("第 {} 线程完成。。。", n);
            });
        }
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {
            sleep(100, TimeUnit.MILLISECONDS);
        }
    }

    public static void main(String[] args) {
        ConcurrentUtil.semaphore();
        ConcurrentUtil.countDownLatch();
        ConcurrentUtil.cyclicBarrier();
    }

}
