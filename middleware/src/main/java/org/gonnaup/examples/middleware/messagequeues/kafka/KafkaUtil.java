package org.gonnaup.examples.middleware.messagequeues.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.io.IOException;
import java.util.Properties;

/**
 * kafka工具类
 *
 * @author gonnaup
 * @version 2021/7/18 16:51
 */
@Slf4j
public class KafkaUtil {

    private KafkaUtil() {
        throw new AssertionError("no KafkaUtil instance for you");
    }

    final static String GROUP = "demo";

    final static String PRODUCER_PROP_FILE = "kafka_producer.properties";

    final static String CONSUMER_PROP_FILE = "kafka_consumer.properties";

    public static KafkaProducer<String, String> newProducer() {
        Properties properties = loadProperties(PRODUCER_PROP_FILE);
        return new KafkaProducer<String, String>(properties);
    }

    public static KafkaConsumer<String, String> newBaseConsumer() {
        Properties properties = loadProperties(CONSUMER_PROP_FILE);
        //设置消费组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP);
        return new KafkaConsumer<>(properties);
    }

    public static KafkaConsumer<String, String> newBaseConsumer(String groupid) {
        Properties properties = loadProperties(CONSUMER_PROP_FILE);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupid);
        return new KafkaConsumer<>(properties);
    }


    static Properties loadProperties(String propFile) {
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream(propFile));
        } catch (IOException e) {
            log.error("配置文件加载错误");
            throw new RuntimeException();
        }
        log.info("配置信息 {}", properties);
        return properties;
    }
}
