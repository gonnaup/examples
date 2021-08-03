package org.gonnaup.examples.middleware.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 客户端工厂类
 *
 * @author gonnaup
 * @version created at 2021/7/24 10:11
 */
@Slf4j
public class LettuceRedisClientFactory {

    /**
     * 连接独立的redis服务端
     *
     * @return
     */
    public static RedisClient standaloneClient() {
        Properties prop = new Properties();
        try (final InputStream resource = ClassLoader.getSystemResourceAsStream("redis.properties")) {
            prop.load(resource);
            final RedisURI redisURI = RedisURI.builder().withHost(prop.getProperty("host")).withPort(Integer.parseInt(prop.getProperty("port"))).withClientName("gonnaup").build();
            return RedisClient.create(redisURI);
        } catch (IOException e) {
            log.error("load redis.properties failed => {}", e.getMessage());
        }
        throw new RuntimeException("connect redis failed! properties => " + prop);
    }

}
