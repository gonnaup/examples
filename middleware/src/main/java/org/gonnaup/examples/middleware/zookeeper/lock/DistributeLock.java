package org.gonnaup.examples.middleware.zookeeper.lock;

/**
 * 分布式锁接口
 * @author gonnaup
 * @version 2021/4/4 20:22
 */
public interface DistributeLock {
    /**
     * 上锁
     * @return
     */
    boolean lock();

    /**
     * 解锁
     * @return
     */
    boolean unlock();
}
