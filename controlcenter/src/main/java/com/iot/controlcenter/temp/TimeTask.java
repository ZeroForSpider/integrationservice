package com.iot.controlcenter.temp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.iot.controlcenter.config.CommonConfig;
import com.iot.iservice.service.IKafkaConfiguration;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ExecutorService;

//@Component
public class TimeTask implements Runnable {
    /**
     * 获取生产者和消费者的配置信息
     */


    private IKafkaConfiguration iKafkaConfiguration;




    private ExecutorService executorService;

    /**
     * 是否继续执行
     */
    private boolean isContinue = true;


//    public TimeTask(IKafkaConfiguration kafkaConfiguration, CustomerGroup customerGroup, ExecutorService executorService) {
//        this.iKafkaConfiguration = kafkaConfiguration;
//        this.customerGroup = customerGroup;
//        this.executorService = executorService;
//        logger.info("TimeTask");
//    }

    public TimeTask() {

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
        CommonConfig commonConfig = new CommonConfig();
        try {
            JSONObject jsonObject = JSON.parseObject(iKafkaConfiguration.getCustomerConfiguration());
            JSONArray jsonArray = jsonObject.getJSONArray("kafkaproperties");
            Map<String, String> map = JSON.parseObject(jsonArray.toJSONString(), new TypeReference<Map<String, String>>() {
            });
            for (Map.Entry<String, String> entry : map.entrySet()) {
                commonConfig.getProperties().put(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            logger.info(e);
        }
        return commonConfig;
    }

    /**
     * 获取生产者配置信息
     *
     * @return
     */
    public CommonConfig getProducerProperties() {
        CommonConfig commonConfig = new CommonConfig();
        try {
            JSONObject jsonObject = JSON.parseObject(iKafkaConfiguration.getProducerConfiguration());
            JSONArray jsonArray = jsonObject.getJSONArray("kafkaproperties");
            Map<String, String> map = JSON.parseObject(jsonArray.toJSONString(), new TypeReference<Map<String, String>>() {
            });
            for (Map.Entry<String, String> entry : map.entrySet()) {
                commonConfig.getProperties().put(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            logger.info(e);
        }
        return commonConfig;
    }

    @Override
    public void run() {
//        logger.info("开始任务调度");
        CommonConfig producerProperties = getProducerProperties();
        CommonConfig customerProperties = getCustomerProperties();
//        logger.info("生产者配置信息如下：" + producerProperties);
//        logger.info("消费者配置信息如下：" + customerProperties.toString());
//        Integer maxThreadNumberOfProducer = Integer.valueOf(producerProperties.getMaxThreadCountOfProducer());
//
//        logger.info("将生产者放入线程池");
//        for (int i = 0; i < maxThreadNumberOfProducer; i++) {
//            executorService.submit(new ProducerHandle(iKafkaConfiguration, producerProperties));
//        }
//        logger.info("将消费者放入线程池");
//        try {
//            customerGroup.CreateThread(Integer.valueOf(customerProperties.getMaxThreadCountOfCustomer()), customerProperties, executorService);
//        } catch (Exception e) {
//            logger.info(e);
//        }
        logger.info("开始执行任务");
//        while (isContinue) {
//            try {
//                executorService.shutdown();
//                //生产者
//                executorService.submit(new ProducerHandle(iKafkaConfiguration, producerProperties));
//                //消费者和消息处理队列
//                customerGroup.CreateThread(customerProperties, executorService);
//            } catch (Exception e) {
//                logger.info("异常" + e);
//            }
//        }
    }
}
