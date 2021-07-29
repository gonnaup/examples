package org.gonnaup.examples.middleware.redis;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * 基于lettuce的redis命令示例<br/>
 * 常用数据结构：<code>string、list、hash、set、sortset</code>
 *
 * @author gonnaup
 * @version created at 2021/7/24 10:00
 */
@Slf4j
public class LettuceRedisCommands {

    /**
     * string结构相关命令示例
     */
    public static void stringStructCommand() {
        final RedisClient redisClient = LettuceRedisClientFactory.standaloneClient();
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisCommands<String, String> commands = connection.sync();
            String key = "website";
            String value = "google";
            commands.set(key, value);
            log.info("get => {}", commands.get(key));
            commands.set(key, value, SetArgs.Builder.ex(Duration.ofMinutes(1)));
            commands.setnx(key, value);
            commands.setex(key, 30, value);
        } finally {
            redisClient.shutdown();
        }
    }

    /**
     * list结构相关命令
     */
    public static void listStructCommand() {
        final RedisClient redisClient = LettuceRedisClientFactory.standaloneClient();
        try (final StatefulRedisConnection<String, String> connect = redisClient.connect()) {
            RedisCommands<String, String> commands = connect.sync();
            String key = "websites";
            String value1 = "google";
            String value2 = "baidu";
            String value3 = "bing";
            commands.lpush(key, value1, value2);
            final List<String> lpops = commands.lpop(key, 2);
            log.info("lpop2 => {}", lpops);
            commands.rpush(key, value2, value3);
            log.info("rpop1 => {}", commands.rpop(key));
            //将value1插入到value2之后
            commands.linsert(key, false, value2, value1);
            commands.lrange(key, 0, commands.llen(key)).stream().reduce(String::concat).ifPresent(s -> log.info(s));
            commands.del(key);
        } finally {
            redisClient.shutdown();
        }
    }

    /**
     * set结构相关命令
     */
    public static void setStructCommand() {
        final RedisClient redisClient = LettuceRedisClientFactory.standaloneClient();
        try (final StatefulRedisConnection<String, String> connect = redisClient.connect()) {
            String key = "websites";
            String value1 = "google";
            String value2 = "baidu";
            String value3 = "bing";
            RedisCommands<String, String> commands = connect.sync();
            commands.sadd(key, value1, value2, value3);
            commands.sismember(key, value1);
            log.info("after pop a member => {}, the length of the set is {}", commands.spop(key), commands.scard(key));
            String key1 = "visited";
            commands.sadd(key, value1, value2);
            commands.sinter(key, key1);//并集
            commands.sunion(key, key1);//交集
            commands.sdiff(key, key1);//差集
            commands.del(key);
            commands.del(key1);
        } finally {
            redisClient.shutdown();
        }
    }

    /**
     * sorted set结构相关命令
     */
    public static void sortedsetStructCommand() {
        final RedisClient redisClient = LettuceRedisClientFactory.standaloneClient();
        try (final StatefulRedisConnection<String, String> connect = redisClient.connect()) {
            String key = "websites";
            String value1 = "google";
            String value2 = "baidu";
            String value3 = "bing";
            RedisCommands<String, String> commands = connect.sync();
            commands.zadd(key, 1D, value1, 2D, value2, 3D, value3);
            commands.zscore(key, value1);//返回value1的score值
            commands.zincrby(key, 5, value1);//将成员value1的score值加上增量5
            commands.zcard(key);//返回key的成员数
            commands.zcount(key, Range.create(2, 10));//返回score值在[2, 10]之间的成员数量
            commands.zrange(key, 0, commands.zcard(key));//按升序返回指定区间的成员
            commands.zrangeWithScores(key, 0, commands.zcard(key));//按升序返回指定区间的成员和score
            commands.zrevrange(key, 0, commands.zcard(key));//降序
            commands.zrangebyscore(key, Range.create(2, 3), Limit.create(0, 2));//返回指定score区间的成员集合，并指定返回个数
            commands.zrangebyscore(key, Range.from(Range.Boundary.including(2), Range.Boundary.excluding(5)));
            commands.zrank(key, value1);
            commands.zrangebylex(key, Range.create("aaaa", "zzzz"));//按字典顺序返回指定字典区间的成员集合
            commands.zlexcount(key, Range.create("aaaa", "bbbb"));
            /**
             * @see RedisCommands#zunionstore(Object, ZStoreArgs, Object[])
             */
            commands.del(key);
        } finally {
            redisClient.shutdown();
        }
    }

    /**
     * hash结构相关命令
     */
    public static void hashStructCommand() {
        final RedisClient redisClient = LettuceRedisClientFactory.standaloneClient();
        try (StatefulRedisConnection<String, String> connect = redisClient.connect()) {
            RedisCommands<String, String> commands = connect.sync();
            String key = "order";
            String id = "98939238";
            String productName = "computer";
            String amount = "1";
            String price = "5000";
            String address = "中国";
            /**
             * @see RedisCommands#hmset(Object, Map)
             */
            commands.hset(key, Map.of("id", id, "productName", productName, "amount", amount, "price", price, "address", address));
            commands.hmget(key, "productName", "ammount", "price");//获取多个field值
            commands.hsetnx(key, "productName", productName);//return false
            commands.hget(key, "productName");
            commands.hexists(key, "productName");
            commands.hdel(key, "address");//删除field
            commands.hlen(key);//field总数
            commands.hstrlen(key, "productName");//field值的长度
            commands.hincrby(key, "amount", 1);//field只能为数字类型
            commands.hkeys(key);//返回所有field
            commands.hvals(key);//返回所有值
            commands.hgetall(key);//返回所有键值对
        } finally {
            redisClient.shutdown();
        }
    }
}
