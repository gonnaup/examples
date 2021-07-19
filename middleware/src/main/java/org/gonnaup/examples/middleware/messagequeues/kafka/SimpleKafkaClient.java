package org.gonnaup.examples.middleware.messagequeues.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author gonnaup
 * @version 2021/7/15 16:31
 * @see org.apache.kafka.clients.CommonClientConfigs
 * @see org.apache.kafka.clients.producer.ProducerConfig
 * @see org.apache.kafka.clients.consumer.ConsumerConfig
 */
@Slf4j
public class SimpleKafkaClient {

    final static String TOPIC = "topic-demo";

    public static void kafkaProducer() {
        KafkaProducer<String, String> producer = KafkaUtil.newProducer();
        List<PartitionInfo> partitionInfos = producer.partitionsFor(TOPIC);
        if (partitionInfos.size() > 0) {
            log.info("producer 连接成功");
        }
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<>(TOPIC, String.format("这是发送的第%d条消息", i)), (metadata, exception) -> log.info("发送成功回调 {}", metadata.toString()));
            log.info("发送第 {} 条消息", i);
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                log.warn(e.getMessage());
            }
        }
        producer.close();
    }

    public static void kafkaConsumer() {
        KafkaConsumer<String, String> consumer = KafkaUtil.newBaseConsumer();
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
        consumer.close();
    }

    public static void main(String[] args) {
        kafkaProducer();
    }

}
