package org.gonnaup.examples.middleware.redis;

import io.lettuce.core.RedisClient;
import lombok.extern.slf4j.Slf4j;

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
     * @return
     */
    public static RedisClient standaloneClient() {
        return RedisClient.create("redis://192.168.0.8/0?clientName=gonnaup");
    }

}
