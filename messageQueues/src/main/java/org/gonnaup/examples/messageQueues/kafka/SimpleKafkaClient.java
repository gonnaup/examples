package org.gonnaup.examples.messageQueues.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author gonnaup
 * @version 2021/7/15 16:31
 */
@Slf4j
public class SimpleKafkaClient {

    final static String TOPIC = "topic-demo";

    final static String GROUP = "demo";

    public static void kafkaProducer() {
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("kafka.properties"));
        } catch (IOException e) {
            log.error("配置文件加载错误");
            return;
        }
        log.info("配置信息 {}", properties);
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<>(TOPIC, String.format("这是发送的第%d条消息", i)));
            log.info("发送第 {} 条消息", i);
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                log.warn(e.getMessage());
            }
        }

    }

    public static void kafkaConsumer() {
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("kafka.properties"));
        } catch (IOException e) {
            log.error("配置文件加载错误");
            return;
        }
        //设置消费组
        properties.put("group.id", GROUP);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(TOPIC));
        int broken = 0;
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            if (records.isEmpty()) {
                broken++;
                if (broken > 100) {
                    log.info("长时间未接收到消息，退出循环");
                    break;
                }
                continue;
            }
            records.forEach(record -> log.info("{} => {}", record.topic(), record.value()));
            broken = 0;
        }
    }

    public static void main(String[] args) {
        new Thread(SimpleKafkaClient::kafkaProducer, "producer").start();
        new Thread(SimpleKafkaClient::kafkaConsumer, "consumer").start();
    }

}
