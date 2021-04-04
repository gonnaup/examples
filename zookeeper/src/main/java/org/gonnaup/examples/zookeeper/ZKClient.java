package org.gonnaup.examples.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author gonnaup
 * @version 2021/4/4 10:06
 */
public class ZKClient {

    public static CuratorFramework newCuratorFramework() {
        return CuratorFrameworkFactory.newClient("localhost", new ExponentialBackoffRetry(1000, 5));
    }

}
