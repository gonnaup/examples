package org.gonnaup.examples.middleware.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import lombok.extern.slf4j.Slf4j;
import org.gonnaup.examples.middleware.Util;

import java.time.Duration;

/**
 * lettuce reactive api
 *
 * @author gonnaup
 * @version created at 2021/8/6 11:08
 */
@Slf4j
public class LettuceReactiveApi {

    public static void reactive() {
        RedisClient client = LettuceRedisClientFactory.standaloneClient();
        try (StatefulRedisConnection<String, String> connect = client.connect()) {
            RedisReactiveCommands<String, String> reactiveCommands = connect.reactive();
            String key = "reactive_key";
            reactiveCommands.set(key, LettuceReactiveApi.class.getName(), SetArgs.Builder.ex(Duration.ofSeconds(20)))
                    .doOnTerminate(() -> log.info("onTerminate"))
                    .doAfterTerminate(() -> log.info("afterTerminate"))
                    .doOnSuccess(s -> log.info("success result {}", s)).subscribe();
            reactiveCommands.get(key).subscribe(value -> log.info("value is => {}", value));
            reactiveCommands.multi().doOnSuccess(log::info).doOnTerminate(reactiveCommands::exec).doAfterTerminate(() -> log.info("事务完成")).subscribe(log::info);//事务模拟
            Util.sleepMilliseconds(500);//等待Flux执行完毕
        } finally {
            client.shutdown();
        }
    }
}
