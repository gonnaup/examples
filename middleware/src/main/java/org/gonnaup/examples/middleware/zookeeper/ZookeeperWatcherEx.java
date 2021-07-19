package org.gonnaup.examples.middleware.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.TimeUnit;

/** 监听器Watcher示例
 * @author gonnaup
 * @version 2021/4/4 10:05
 */
@Slf4j
public class ZookeeperWatcherEx {

    public static void watcher_eg() {
        String path0 = "/test/watcher/node-1";
        CuratorEx.createNode(path0, "watcher");
        CuratorFramework curatorFramework = ZKClient.curatorFramework();
        try {
            curatorFramework.start();
            curatorFramework.checkExists()
                    .usingWatcher((Watcher) event -> {
                        Watcher.Event.EventType eventType = event.getType();
                        log.info("eventType {}", eventType.name());
                        String path = event.getPath();
                        log.info("changed path {}", path);
                    })
                    .forPath(path0);
            CuratorEx.updateNode(path0, "hello watcher");
            CuratorEx.updateNode(path0, "hello watcher0");
            TimeUnit.SECONDS.sleep(5);
            CuratorEx.deleteNode(path0);
            curatorFramework.close();
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        watcher_eg();
    }

}
