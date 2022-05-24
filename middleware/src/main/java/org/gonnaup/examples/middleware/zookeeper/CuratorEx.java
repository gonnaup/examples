package org.gonnaup.examples.middleware.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.gonnaup.examples.middleware.ConstProperties;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * zookeeper curator客户端示例
 *
 * @author gonnaup
 * @version 2021/4/3 13:53
 */
@Slf4j
public class CuratorEx {

    public static boolean createNode(String path, String payloadstring) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(payloadstring);
        CuratorFramework client = CuratorFrameworkFactory.newClient(String.format("%s:2181", ConstProperties.SERVER_ADDRESS), new ExponentialBackoffRetry(1000, 5));
        try (client) {
            client.start();//启动并连接服务器
            byte[] payload = payloadstring.getBytes(StandardCharsets.UTF_8);
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(path, payload);
            log.info("创建节点 {} 成功，数据为 {}", path, payloadstring);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public static String readNode(String path) {
        Objects.requireNonNull(path);
        CuratorFramework client = CuratorFrameworkFactory.newClient(String.format("%s:2181", ConstProperties.SERVER_ADDRESS), new ExponentialBackoffRetry(1000, 5));
        try (client) {
            client.start();
            Stat stat = client.checkExists().forPath(path);
            if (stat != null) {
                byte[] bytes = client.getData().forPath(path);
                String data = new String(bytes, StandardCharsets.UTF_8);
                log.info("节点 {} 的数据 {}", path, data);
                return data;
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static boolean updateNode(String path, String payloadstring) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(payloadstring);
        CuratorFramework client = CuratorFrameworkFactory.newClient(String.format("%s:2181", ConstProperties.SERVER_ADDRESS), new ExponentialBackoffRetry(1000, 5));
        try (client) {
            client.start();
            client.setData()
                    .forPath(path, payloadstring.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public static boolean deleteNode(String path) {
        Objects.requireNonNull(path);
        CuratorFramework client = CuratorFrameworkFactory.newClient(String.format("%s:2181", ConstProperties.SERVER_ADDRESS), new ExponentialBackoffRetry(1000, 5));
        try (client) {
            client.start();
            client.delete()
                    .forPath(path);
            log.info("删除节点 {}", path);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        String nodePath = "/test/node-1";
        String payload = "hello, zookeeper";
        createNode(nodePath, payload);
        readNode(nodePath);
        updateNode(nodePath, payload + " updated");
        readNode(nodePath);
        deleteNode(nodePath);
    }
}
