package org.gonnaup.examples.middleware.messagequeues.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 超时过滤拦截器，通过<code>interceptor.classes</code>配置，多个拦截器使用','隔开
 * 调用顺序按配置的前后顺序
 *
 * @author gonnaup
 * @version created at 2021/7/21 11:08
 */
@Slf4j
public class ConsumerInterceptorTTL implements ConsumerInterceptor<String, String> {

    private static final long EXPIRE_INTERVAL = 10 * 1000;//超时10s

    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
        long now = System.currentTimeMillis();
        Map<TopicPartition, List<ConsumerRecord<String, String>>> filtedData = records.partitions().stream().collect(() -> new HashMap<TopicPartition, List<ConsumerRecord<String, String>>>(),
                (collectorMap, topicPartition) -> collectorMap.put(topicPartition,
                        records.records(topicPartition).stream().filter(consumerRecord -> now - consumerRecord.timestamp() < EXPIRE_INTERVAL).collect(Collectors.toList())),//过滤过期数据
                (leftCollector, rightCollector) -> leftCollector.putAll(rightCollector));
        return new ConsumerRecords<>(filtedData);
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
        log.info("commit offset {}", offsets);
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> configs) {
    }

}
