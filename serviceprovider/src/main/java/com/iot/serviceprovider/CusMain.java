package com.iot.serviceprovider;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import sun.security.provider.MD5;

import java.util.*;

public class CusMain {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "119.29.193.187:9092");
        properties.put("group.id", "group-1");
        properties.put("enable.auto.commit", "false");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
//        kafkaConsumer.subscribe(Arrays.asList("htl"));
        kafkaConsumer.assign(Arrays.asList(new TopicPartition("htl", 0)));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (TopicPartition partition : records.partitions()) {
                System.out.println("############33333");
                System.out.println(partition);
            }
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, value = %s partitions:=%s", record.offset(), record.value(), record.partition());
                System.out.println();
            }
            MD5 string = new MD5();
        }

    }
}
