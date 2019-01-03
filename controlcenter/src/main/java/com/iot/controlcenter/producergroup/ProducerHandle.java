package com.iot.controlcenter.producergroup;

import com.alibaba.fastjson.JSON;
import com.iot.controlcenter.ThreadManage.ThreadPoolUtils;
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

    /**
     * 生产者实例
     */
    private static KafkaProducer<String, String> kafkaProducer = null;
    /**
     * 日志
     */
    public Logger logger = Logger.getLogger(ProducerHandle.class);


    /**
     * 生产者配置信息
     */
    private CommonConfig commonConfig;

    /**
     * 产生设备信息
     *
     * @return
     */
    public static String randMacAddress() {
        String SEPARATOR_OF_MAC = ":";
        Random random = new Random();
        String[] mac = {
                String.format("%02x", 0x52),
                String.format("%02x", 0x54),
                String.format("%02x", 0x00),
                String.format("%02x", random.nextInt(0xFF)),
                String.format("%02x", random.nextInt(0xFF)),
                String.format("%02x", random.nextInt(0xFF))
        };
        return String.join(SEPARATOR_OF_MAC, mac);
    }


    public ProducerHandle() {
    }

    public ProducerHandle(CommonConfig commonConfig) {
        this.commonConfig = commonConfig;
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
        for (int i = 0; i < 10; i++) {
            synchronized (ProducerHandle.class) {
                macAddress = randMacAddress();
                timeStamp = String.valueOf(System.currentTimeMillis());
                Thread.sleep(10);
            }
            concurrentHashMap.put(UUID.randomUUID().toString(), new Message(
                    macAddress,
                    macAddress.hashCode() % 2 == 0 ? (flag == 0 ? 1 : 0) : (flag == 1 ? 0 : 1),
                    timeStamp
            ));
        }
        return concurrentHashMap;
    }

    @Override
    public void run() {
        if (kafkaProducer == null) {
            kafkaProducer = new KafkaProducer<>(commonConfig.getProperties());
            logger.info(commonConfig.getProperties());
        }
        try {
            logger.info(ThreadPoolUtils.isShutdownThread);
            int flag = 0;
            while (!ThreadPoolUtils.isShutdownThread) {
                ConcurrentHashMap<String, Message> putMessage = getPushMessage(flag);
                flag = flag == 0 ? 1 : 0;
                if (putMessage.size() != 0) {
                    for (Map.Entry<String, Message> entry : putMessage.entrySet()) {
                        kafkaProducer.send(
                                new ProducerRecord<>(commonConfig.getTopic(),
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
