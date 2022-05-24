package org.gonnaup.examples.middleware.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.gonnaup.examples.middleware.ConstProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author gonnaup
 * @version 2021/4/4 10:06
 */
@Slf4j
public class ZKClient {

    public static CuratorFramework curatorFramework() {
        return ZKClientHolder.INSTANCE.curatorFramework;
    }

    enum ZKClientHolder {

        INSTANCE;

        public final CuratorFramework curatorFramework;

        private ZKClientHolder() {
            curatorFramework = CuratorFrameworkFactory.newClient("", new ExponentialBackoffRetry(1000, 5));
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper(String.format("%s:2181", ConstProperties.SERVER_ADDRESS), 15000, watchedEvent -> log.info(watchedEvent.getType().name()));
        zooKeeper.create("/test/connect", "hello".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.exists("/test/connect", watchedEvent -> log.info(watchedEvent.getPath() + "\t" + watchedEvent.getType().name()));
        zooKeeper.close();
    }

}
