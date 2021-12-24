package org.gonnaup.examples.javase.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * @author gonnaup
 * @version created at 2021/12/24 12:40
 */
@Slf4j
public class SynchronizedProductionLine implements ProductionLine {

    private int product_count;

    private final Object monitor = new Object();

    @Override
    @SuppressWarnings("all")
    public void produce() {
        while (true) {
            synchronized (monitor) {
                if (product_count < 100) {
                    log.info("生产产品，总产品数{}", ++product_count);
                    monitor.notifyAll();
                } else {
                    synchronized (monitor) {
                        log.info("产品数量不能超过100，等待消费者消费。。。");
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            log.error(e.getMessage());
                        }
                    }
                }

            }
        }
    }

    @Override
    @SuppressWarnings("all")
    public void consume() {
        while (true) {
            synchronized (monitor) {
                if (product_count > 0) {
                    log.info("消费产品，剩余总产品数{}", --product_count);
                    monitor.notifyAll();
                } else {
                    log.info("产品数量为0，等待生产者生产。。。");
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        ProductionLineStater productionLineStater = new ProductionLineStater(new SynchronizedProductionLine());
        productionLineStater.startProductionLine();
    }

}
