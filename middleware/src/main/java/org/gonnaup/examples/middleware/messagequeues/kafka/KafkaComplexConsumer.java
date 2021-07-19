package org.gonnaup.examples.middleware.messagequeues.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.gonnaup.examples.middleware.JsonUtil;
import org.gonnaup.examples.middleware.messagebody.Product;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 复杂情况下的kafka consumer处理
 *
 * @author gonnaup
 * @version 2021/7/18 16:41
 */
@Slf4j
public class KafkaComplexConsumer {

    /**
     * 最基础的消费端,自动提交offset
     * @param max_count 接收信息条数
     */
    public static void baseConsumer(int max_count) {
        KafkaConsumer<String, String> consumer = KafkaUtil.newBaseConsumer();
        try (consumer) {
            consumer.subscribe(Collections.singleton(SimpleKafkaClient.TOPIC));
            short count = 0;
            do {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                log.info("本次拉取记录数 {}", records.count());
                for (ConsumerRecord<String, String> record : records) {
                    log.info("topic = {}, partition = {}, ofset = {}, value = {}", record.topic(), record.partition(), record.offset(), JsonUtil.parseJSON(record.value(), Product.class));
                    //模拟处理时间
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                }
            } while (count < max_count);
        }
    }

}
