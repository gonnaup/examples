package org.gonnaup.examples.middleware.zookeeper.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.ZooDefs;
import org.gonnaup.examples.middleware.zookeeper.ZKClient;

/** 基于zookeeper的分布式锁
 * @author gonnaup
 * @version 2021/4/4 20:27
 */
@Slf4j
public class ZookeeperDistributeLock implements DistributeLock {

    /* 锁根节点 */
    private String rootpath = "/distributelock";

    /* 当前锁目录节点 */
    private String path = "seq-";

    private CuratorFramework client = null;

    public ZookeeperDistributeLock() {
        client = ZKClient.curatorFramework();
        //检查锁根节点
        try {
            if (client.checkExists().forPath(rootpath) == null) {
                client.create().withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE).forPath(rootpath);
            }
        } catch (Exception e) {
            log.error("zookeeper operate error! {}", e.getMessage());
        }
    }

    @Override
    public boolean lock() {
        CuratorFramework curatorFramework = ZKClient.curatorFramework();
        return false;
    }

    @Override
    public boolean unlock() {
        // TODO Auto-generated method stub
        return false;
    }
}
