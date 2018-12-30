package com.iot.controlcenter.producergroup;

import com.alibaba.fastjson.JSON;
import com.iot.controlcenter.config.CommonConfig;
import com.iot.controlcenter.model.Message;
import com.iot.iservice.service.IKafkaConfiguration;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProducerHandle implements Runnable {

    @Autowired
    private IKafkaConfiguration iKafkaConfiguration;


    private static KafkaProducer<String, String> kafkaProducer = null;
    /**
     * 日志
     */
    public Logger logger = Logger.getLogger(ProducerHandle.class);


    private CommonConfig commonConfig;

    public static String randMacAddress() {
        String SEPARATOR_OF_MAC = ":";
        Random random = new Random();
        String[] mac = {
                String.format("%02x", 0x52),
                String.format("%02x", 0x54),
                String.format("%02x", 0x00),
                String.format("%02x", random.nextInt(0x01)),
                String.format("%02x", random.nextInt(0x0F)),
                String.format("%02x", random.nextInt(0xF0))
        };
        return String.join(SEPARATOR_OF_MAC, mac);
    }


    public ProducerHandle() {
    }

    public ProducerHandle(IKafkaConfiguration iKafkaConfiguration, CommonConfig commonConfig) {
        this.iKafkaConfiguration = iKafkaConfiguration;
        this.commonConfig = commonConfig;
    }

    public Properties getProducerProperties() {
        Map<String, String> map = iKafkaConfiguration.getProducerConfiguration();
        Properties properties = new Properties();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }
        logger.info("properties  " + properties);
        //   Properties properties = new Properties();
//        properties.put("bootstrap.servers", "119.29.193.187:9092");
//        properties.put("acks", "all");
//        properties.put("retries", 0);
//        properties.put("batch.size", 16384);
//        properties.put("linger.ms", 1);
//        properties.put("buffer.memory", 33554432);
//        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return properties;
    }

    /**
     * 得到需要推送的消息
     *
     * @param flag
     * @return
     */
    public ConcurrentHashMap<String, Message> getPushMessage(int flag) throws InterruptedException {
        String macAddress = "";
        String timeStamp = "";
        ConcurrentHashMap<String, Message> concurrentHashMap = new ConcurrentHashMap<>();
        synchronized (ProducerHandle.class) {
            macAddress = randMacAddress();
            timeStamp = String.valueOf(System.currentTimeMillis());
            Thread.sleep(100);
        }
        for (int i = 0; i < 100; i++) {
            concurrentHashMap.put(UUID.randomUUID().toString(), new Message(
                    macAddress,
                    macAddress.hashCode() % 2 == 0 ? flag == 0 ? 1 : 0 : flag == 1 ? 0 : 1,
                    timeStamp
            ));
        }
        return concurrentHashMap;
    }

    @Override
    public void run() {
        if (kafkaProducer == null) {
            kafkaProducer = new KafkaProducer<>(commonConfig.properties);
        }
        try {
            int flag = 0;
            while (true) {
                ConcurrentHashMap<String, Message> putMessage = getPushMessage(flag);
                flag = flag == 0 ? 1 : 0;
                if (putMessage.size() != 0) {
                    for (Map.Entry<String, Message> entry : putMessage.entrySet()) {
                        kafkaProducer.send(
                                new ProducerRecord<>(commonConfig.TopicKey,
                                        String.valueOf(entry.getValue().deviceMacAddress.hashCode()),
                                        JSON.toJSONString(entry.getValue()))
                        );
                        logger.info("发送消息中" + JSON.toJSONString(entry.getValue()));
                    }
                }
            }
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("生产者发生异常了" + e);
            }
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info("发生异常，关闭生产者");
            }
        }
    }
}
