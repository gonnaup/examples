package org.gonnaup.examples.middleware.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * lettuce asynchronous api using <code>future</code>
 *
 * @author gonnaup
 * @version created at 2021/8/6 8:12
 * @see java.util.concurrent.CompletableFuture
 */
@Slf4j
public class LettuceAsynchronousApi {

    public static void asynchronous() {
        final RedisClient client = LettuceRedisClientFactory.standaloneClient();
        try (StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisAsyncCommands<String, String> asyncCommands = connection.async();
            String key = "key_asynchronous";

            final RedisFuture<String> key_asynchronous = asyncCommands.set(key, LettuceAsynchronousApi.class.getName());
            key_asynchronous.thenAccept(s -> log.info("<asynchronized> {} value return => [{}]", Thread.currentThread().getName(), s));

            RedisFuture<String> keyAsynchronous = asyncCommands.get(key);
            keyAsynchronous.thenAcceptAsync(s -> log.info("<asynchronized> {} value return => [{}]", Thread.currentThread().getName(), s));
            try {
                //RedisFuture等待命令返回
                while (!keyAsynchronous.await(1, TimeUnit.MILLISECONDS)) {
                    log.info("continue wait for result available");
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }

            RedisFuture<String> keyAsynchronous1 = asyncCommands.get(key);
            keyAsynchronous1.thenApply(String::length).thenAcceptAsync(len -> log.info("<asynchronized> {} value length => {}", Thread.currentThread().getName(), len));
            //Exception handle
            keyAsynchronous1.handle((value, throwable) -> {
                if (throwable != null) {
                    return "default value";
                }
                return value;
            });
            keyAsynchronous1.exceptionally(throwable -> "default value");

            asyncCommands.del(key);
        } finally {
            client.shutdown();
        }
    }
}
