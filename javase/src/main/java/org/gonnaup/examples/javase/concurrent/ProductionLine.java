package org.gonnaup.examples.javase.concurrent;

/**
 * @author gonnaup
 * @version created at 2021/12/24 12:32
 */
public interface ProductionLine {

    /**
     * 生产
     */
    void produce();

    /**
     * 消费
     */
    void consume();
}
