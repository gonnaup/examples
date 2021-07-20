package org.gonnaup.examples.middleware.messagequeues.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.gonnaup.examples.middleware.JsonUtil;
import org.gonnaup.examples.middleware.messagebody.Product;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 复杂情况下的kafka consumer处理
 *
 * @author gonnaup
 * @version 2021/7/18 16:41
 */
@Slf4j
public class KafkaComplexConsumer {

    /*----------------- 基本消息拉取 ---------------*/

    /**
     * 最基础的消费端,自动提交offset
     *
     * @param max_count 接收信息条数
     */
    public static void baseConsumer(int max_count) {
        KafkaConsumer<String, String> consumer = KafkaUtil.newBaseConsumer();
        try (consumer) {
            consumer.subscribe(Collections.singleton(SimpleKafkaClient.TOPIC));
            pollMessage(max_count, consumer);
        }
    }

    /**
     * 订阅指定的分区
     *
     * @param max_count
     */
    public static void assinSpecialPartitions(int max_count) {
        KafkaConsumer<String, String> consumer = KafkaUtil.newBaseConsumer();
        TopicPartition topicPartition = new TopicPartition(SimpleKafkaClient.TOPIC, 0);
        consumer.assign(Collections.singleton(topicPartition));//只订阅topic的第0个分区
        pollMessage(max_count, consumer);
    }

    /*------------------------ 位移手动提交 --------------------------*/

    /**
     * 自动位移提交的方式在正常情况下不会发生消息丢失或重复消费的情况，当当在拉取消费过程中出现异常，
     * 将会出现这些问题。
     */

    public static void manualCommitOffset(int max_count) {
        Properties properties = KafkaUtil.loadProperties("kafka_consumer.properties");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaUtil.GROUP);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);//关闭自动提交
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "manualCommitOffset");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        try (consumer) {
            consumer.subscribe(Collections.singleton(SimpleKafkaClient.TOPIC));
            //consumer.commitSync();//手动同步提交,根据poll()方法拉取的最新位移
            //使用带参数的commitSync()来进行更细颗粒度的提交
            final AtomicInteger count = new AtomicInteger();
            do {
                /*--- 每消费一条消息提交一次，存在一定的性能问题 ---*/
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
//                for (ConsumerRecord<String, String> record : records) {
//                    log.info("获取消息 {}", JsonUtil.parseJSON(record.value(), Product.class));
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(100);//模拟逻辑处理时间
//                    } catch (InterruptedException e) {
//                        log.error(e.getMessage());
//                    }
//                    long offset = record.offset();//当前记录的offset
//                    TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());//当前记录的topic和partition
//                    //手动提交当前消费消息的offset，每消费一条提交一次，会耗费一定的性能
//                    consumer.commitSync(Collections.singletonMap(topicPartition, new OffsetAndMetadata(offset + 1)));
//                    count++;
//                }
                /*---按分区粒度同步提交消费位移---*/
                records.partitions().forEach(topicPartition -> {
                    //按分区维度获取消费记录
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(topicPartition);
                    partitionRecords.forEach(consumerRecord -> {
                        log.info("获取消息 {}", JsonUtil.parseJSON(consumerRecord.value(), Product.class));
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);//模拟逻辑处理时间
                        } catch (InterruptedException e) {
                            log.error(e.getMessage());
                        }
                        count.incrementAndGet();
                    });
                    //提交此分区消费后的offset
                    long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                    consumer.commitSync(Collections.singletonMap(topicPartition, new OffsetAndMetadata(lastOffset + 1)));
                });
            } while (count.get() >= max_count);
        }

    }









    /*----------------------private method---------------------------*/

    /**
     * 拉取逻辑
     *
     * @param max_count
     * @param consumer
     * @param count
     */
    private static void pollMessage(int max_count, KafkaConsumer<String, String> consumer) {
        int count = 0;
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
