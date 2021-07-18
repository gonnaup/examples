/**
 * @author gonnaup
 * @version 2021/7/16 16:55
 * <p>
 * kafka主要配置参数：
 * broker.id=0    #broker的编号，如果集群中有多个broker，则每个broker的编号需要设置不同
 * listeners=PLAINTEXT://localhost:9092    #broker对外提供的服务入口地址
 * log.dirs=/tmp/kafka-logs    #存放消息日志文件的地址
 * zookeeper.connect=localhost:2181    #kafka所需的zookeeper集群地址，多个节点是用逗号隔开
 * <p>
 * <p>
 * kafka脚本：
 * 1.kafka启动：
 * $KAFKA_HOME/bin/kafka-server-start.sh config/server.properties
 * 2.kafka创建topic"topic-demo"，副本因子3，分区数4
 * $KAFKA_HOME/bin/kafka-topics.sh --zookeeper localhost:2181 -- create -- topic topic-demo -- replication-factor 3  -- partitions  4
 * 3.主题信息查看
 * $KAFKA_HOME/bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic topic-demo
 * 4.消费信息
 * $KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-demo
 * 5.生产信息
 * $KAFKA_HOME/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic topic-demo
 */
package org.gonnaup.examples.middleware.messagequeues.kafka;