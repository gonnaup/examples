package org.gonnaup.examples.middleware.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.event.command.CommandFailedEvent;
import io.lettuce.core.event.command.CommandListener;
import io.lettuce.core.event.command.CommandStartedEvent;
import io.lettuce.core.event.command.CommandSucceededEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用Lettuce连接redis
 *
 * @author gonnaup
 * @version created at 2021/7/22 11:06
 */
@Slf4j
public class SimpleLettuceClient {

    /**
     * standalone client
     *
     * <p>
     *    <ul>
     *        <li><a href = "https://github.com/lettuce-io/lettuce-core/blob/6.1.4.RELEASE/src/test/java/io/lettuce/examples/ConnectToRedisUsingRedisSentinel.java">Redis Sentinel</a></li>
     *        <li><a href="https://github.com/lettuce-io/lettuce-core/blob/6.1.4.RELEASE/src/test/java/io/lettuce/examples/ConnectToRedisCluster.java">Redis Cluster</a></li>
     *    </ul>
     * </p>
     */
    public static void redisConnect() {
        RedisClient redisClient = RedisClient.create("redis://192.168.0.8/0?clientName=gonnaup");
//        RedisURI redisURI = RedisURI.create("192.168.0.8", 6379);
//        redisURI.setClientName("gonnaup");
//        RedisClient redisClient = RedisClient.create(redisURI);
        redisClient.addListener(new CommandListener() {
            @Override
            public void commandStarted(CommandStartedEvent event) {
                log.info("commandStarted...");
            }

            @Override
            public void commandSucceeded(CommandSucceededEvent event) {
                String typeName = event.getCommand().getType().name();
                log.info("commandSuccessed..., the type is {}", typeName);
            }

            @Override
            public void commandFailed(CommandFailedEvent event) {
                log.info("commandFailed...");
            }
        });
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> commands = connection.sync();
        String clientName = commands.clientGetname();
        commands.set("test", "test data");
        String test = commands.get("test");
        log.info("this client id is {}, name is {}, test info {}", commands.clientId(), clientName, test);
        commands.del("test");
        redisClient.shutdown();
    }

}
