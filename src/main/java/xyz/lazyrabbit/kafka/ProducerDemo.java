package xyz.lazyrabbit.kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.Future;

public class ProducerDemo {

    public static void main(String[] args) {
        // 1. 配置
        Properties properties = new Properties();
        // bootstrap.servers kafka集群地址 host1:port1,host2:port2 ....
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.137.101:9092,192.168.137.102:9092,192.168.137.103:9092");
        // key.deserializer 消息key序列化方式
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // value.deserializer 消息体序列化方式
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 生产者要求请求完成之前leader收到的确认数。
        // 1 leader将记录写入其本地日志，无需等待所有副本服务器的完全确认即可做出回应
        //    在这种情况下，如果leader在确认记录后立即失败，但在将数据复制到所有的副本服务器之前，则记录将会丢失。
        // all/-1 leader将等待完整的同步副本集以确认记录，这保证了只要至少一个同步副本服务器仍然存活，记录就不会丢失
        // 0 生产者将不会等待来自服务器的任何确认，在这种情况下，无法保证服务器已收到记录，并且重试配置将不会生效
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        // 一个批次使用内存大小
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // 在发送前等待的时间
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 100);
        // 发送最大请求大小
        properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 1 * 1024 * 1024);
        // 2. 创建生产者实例
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        // 3. 发送消息
        // 3.1 异步发送消息
        for (int i = 0; i < 10; i++) {
            String data = "async :" + i;
            // 发送消息
            producer.send(new ProducerRecord<>("demo-topic", data));
        }
        // 3.2 同步发送消息 调用get()阻塞返回结果
        for (int i = 0; i < 10; i++) {
            String data = "sync : " + i;
            try {
                // 发送消息
                Future<RecordMetadata> send = producer.send(new ProducerRecord<>("demo-topic", data));
                RecordMetadata recordMetadata = send.get();
                System.out.println(recordMetadata);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        // 3.3 异步发送消息 回调callback()
        for (int i = 0; i < 10; i++) {
            String data = "callback : " + i;
            // 发送消息
            producer.send(new ProducerRecord<>("demo-topic", data), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    // 发送消息的回调
                    if (exception != null) {
                        exception.printStackTrace();
                    } else {
                        System.out.println(metadata);
                    }
                }
            });
        }

        producer.close();
    }
}
