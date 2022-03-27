package xyz.lazyrabbit.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemo {

    public static void main(String[] args) {
        // 1. 配置
        Properties properties = new Properties();
        //bootstrap.servers kafka集群地址 host1:port1,host2:port2 ....
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.137.101:9092,192.168.137.102:9092,192.168.137.103:9092");
        // key.deserializer 消息key序列化方式
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // value.deserializer 消息体序列化方式
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // group.id 消费组id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "demo-group");
        // enable.auto.commit 设置自动提交offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        // auto.offset.reset 偏移量设置
        // earliest 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
        // latest 当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
        // none topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // 2. 创建消费者实例并订阅topic
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        String[] topics = new String[]{"demo-topic"};
        consumer.subscribe(Arrays.asList(topics));

        // 3. 消费消息
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record);
            }
        }
    }
}
