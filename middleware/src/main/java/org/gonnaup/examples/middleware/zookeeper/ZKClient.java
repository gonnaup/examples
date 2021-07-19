package org.gonnaup.examples.middleware.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author gonnaup
 * @version 2021/4/4 10:06
 */
public class ZKClient {

    private static final CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost", new ExponentialBackoffRetry(1000, 5));

    public static CuratorFramework curatorFramework() {
        return curatorFramework;
    }

}
