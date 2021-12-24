package org.gonnaup.examples.javase.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gonnaup
 * @version created at 2021/12/24 14:45
 */
@Slf4j
public class ReentrantLockProductionLine extends ReentrantLock implements ProductionLine {

    private static final long serialVersionUID = 8618227252853355772L;

    private int product_count;

    private final Condition condition = this.newCondition();

    @Override
    @SuppressWarnings("all")
    public void produce() {
        while (true) {
            if (product_count < 100) {
                try {
                    lock();
                    product_count += 1;
                    log.info("生产产品，总产品数{}", product_count);
                    condition.signalAll();
                } finally {
                    unlock();
                }
            } else {
                try {
                    lock();
                    if (product_count >= 100) {//未进行全锁定，必须进行双重判定，否则造成死锁(运行到此处后切换到消费者线程消费所有产品后等待，再切到此线程此处)
                        log.info("产品数量不能超过100，等待消费者消费。。。");
                        condition.await();
                    }
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                } finally {
                    unlock();
                }
            }
        }
    }

    @Override
    @SuppressWarnings("all")
    public void consume() {
        while (true) {
            if (product_count > 0) {
                try {
                    lock();
                    product_count -= 1;
                    log.info("消费产品，剩余总产品数{}", product_count);
                    condition.signalAll();
                } finally {
                    unlock();
                }
            } else {
                try {
                    lock();
                    if (product_count == 0) {//未进行全锁定，必须进行双重判定，否则造成死锁
                        log.info("产品数量为0，等待生产者生产。。。");
                        condition.await();
                    }
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                } finally {
                    unlock();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProductionLineStater productionLineStater = new ProductionLineStater(new ReentrantLockProductionLine());
        productionLineStater.startProductionLine();
    }
}
