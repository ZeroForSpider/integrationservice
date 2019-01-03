package com.iot.controlcenter.customerGroup;

import com.iot.controlcenter.ThreadManage.ThreadPoolUtils;
import com.iot.controlcenter.config.CommonConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * 消费都处理类
 * @author LHT
 */
public class CustomerHandle implements Runnable {

    /**
     * 日志类
     */
    public static Logger logger = Logger.getLogger(CustomerHandle.class);


    /**
     * 配置信息
     */
    private CommonConfig commonConfig;

    @Override
    public void run() {
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(commonConfig.getProperties());
        try {
            logger.info("RUN");
            kafkaConsumer.subscribe(Arrays.asList(commonConfig.getTopic()));
            while (!ThreadPoolUtils.isShutdownThread) {
                logger.info("轮询消息");
                ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("接受消息");
                    logger.info(record.value());
                    MessageQueen.addMessageToQueen(record.value());
                }
            }
        } catch (Exception e) {
            logger.info("Exception " + e);
        }finally {
            kafkaConsumer.close();
            logger.info(Thread.currentThread().getName()+"消费者被关闭");
        }
    }


    public CustomerHandle(CommonConfig commonConfig) {
        this.commonConfig = commonConfig;
    }
}