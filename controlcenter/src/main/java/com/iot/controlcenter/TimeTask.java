package com.iot.controlcenter;

import com.iot.controlcenter.config.CommonConfig;
import com.iot.controlcenter.customerGroup.CustomerGroup;
import com.iot.controlcenter.producergroup.ProducerHandle;
import com.iot.iservice.service.IKafkaConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TimeTask implements Runnable {
    /**
     * 获取生产者和消费者的配置信息
     */

    private IKafkaConfiguration iKafkaConfiguration;


    private CustomerGroup customerGroup;

    public TimeTask(IKafkaConfiguration iKafkaConfiguration, CustomerGroup customerGroup) {
        this.iKafkaConfiguration = iKafkaConfiguration;
        this.customerGroup = customerGroup;
    }

    /**
     * 日志
     */
    private Logger logger = Logger.getLogger(TimeTask.class);

    /**
     * 获取消费者配置信息
     *
     * @return
     */
    public CommonConfig getCustomerProperties() {
        Map<String, String> map = iKafkaConfiguration.getCustomerConfiguration();
        CommonConfig customerProperties = new CommonConfig("1");
        if (!map.containsKey(customerProperties.TopicKey)) {
            customerProperties.TopicKey = "htl";
        } else {
            customerProperties.TopicKey = map.get(customerProperties.TopicKey);
            map.remove(customerProperties.TopicKey);
        }
        if (!map.containsKey(customerProperties.MaxThreadCountKeyOfCustomer)) {
            customerProperties.MaxThreadCountKeyOfCustomer = "3";
        } else {
            customerProperties.MaxThreadCountKeyOfCustomer = map.get(customerProperties.MaxThreadCountKeyOfCustomer);
            map.remove(customerProperties.MaxThreadCountKeyOfCustomer);
        }
        if (!map.containsKey(customerProperties.IsUpdateKeyOfCustomer)) {
            customerProperties.IsUpdateKeyOfCustomer = "0";
        } else {
            customerProperties.IsUpdateKeyOfCustomer = map.get(customerProperties.IsUpdateKeyOfCustomer);
            map.remove(customerProperties.IsUpdateKeyOfCustomer);
        }
        if (map.size() != 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                customerProperties.properties.put(entry.getKey(), entry.getValue());
            }
        }
        return customerProperties;
    }

    /**
     * 获取生产者配置信息
     *
     * @return
     */
    public CommonConfig getProducerProperties() {
        Map<String, String> map = iKafkaConfiguration.getProducerConfiguration();
        CommonConfig producerProperties = new CommonConfig("!");
        if (!map.containsKey(producerProperties.TopicKey)) {
            producerProperties.TopicKey = "htl";
        } else {
            producerProperties.TopicKey = map.get(producerProperties.TopicKey);
            map.remove(producerProperties.TopicKey);
        }
        if (!map.containsKey(producerProperties.MaxThreadCountKeyOfProducer)) {
            producerProperties.MaxThreadCountKeyOfProducer = "3";
        } else {
            producerProperties.MaxThreadCountKeyOfProducer = map.get(producerProperties.MaxThreadCountKeyOfCustomer);
            map.remove(producerProperties.MaxThreadCountKeyOfCustomer);
        }
        if (!map.containsKey(producerProperties.IsUpdateKeyOfCustomer)) {
            producerProperties.IsUpdateKeyOfCustomer = "0";
        } else {
            producerProperties.IsUpdateKeyOfCustomer = map.get(producerProperties.IsUpdateKeyOfCustomer);
            map.remove(producerProperties.IsUpdateKeyOfCustomer);
        }
        if (map.size() != 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                try{
                    producerProperties.properties.put(entry.getKey(), entry.getValue());
                }catch (Exception e){
                    logger.info(e);
                }
            }
        }
        return producerProperties;
    }

    @Override
    public void run() {
        logger.info("开始任务调度");
        CommonConfig producerProperties = getProducerProperties();
        CommonConfig customerProperties = getCustomerProperties();
       // logger.info("生产者配置信息如下：" + producerProperties);
    //    logger.info("消费者配置信息如下：" + customerProperties.toString());
//        Integer maxThreadNumber = Integer.valueOf(producerProperties.MaxThreadCountKeyOfProducer) + Integer.valueOf(customerProperties.MaxThreadCountKeyOfCustomer) + 1;
//        ExecutorService executorService = Executors.newFixedThreadPool(Integer.valueOf(maxThreadNumber));
//        logger.info("将生产者放入线程池");
//        for (int i = 0; i < Integer.valueOf(producerProperties.MaxThreadCountKeyOfProducer); i++) {
//            executorService.submit(new ProducerHandle(iKafkaConfiguration, producerProperties));
//        }
        logger.info("将消费者放入线程池");
       try {
           customerGroup.CreateThread(Integer.valueOf(customerProperties.MaxThreadCountKeyOfCustomer), customerProperties, false);
       }catch (Exception e){
           logger.info(e);
       }
    }
}
