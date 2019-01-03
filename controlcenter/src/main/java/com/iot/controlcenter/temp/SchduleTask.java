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

public class SchduleTask implements Runnable {

    private IKafkaConfiguration iKafkaConfiguration;

    private static Logger logger = Logger.getLogger(SchduleTask.class);

    private ExecutorService executorService;

//    private CustomerGroup customerGroup;

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
        logger.info("run" + "SchduleTask");
        CommonConfig customerProperties = getCustomerProperties();
        CommonConfig producerProperties = getProducerProperties();
//        if (customerProperties.getIsUpdateOfCustomer().equals("1") || producerProperties.getIsUpdateOfProducer().equals("1")) {
//            executorService.submit(new TimeTask(iKafkaConfiguration, customerGroup, executorService));
//        }
    }

//    public SchduleTask(IKafkaConfiguration iKafkaConfiguration, ExecutorService executorService, CustomerGroup customerGroup) {
//        this.iKafkaConfiguration = iKafkaConfiguration;
//        this.executorService = executorService;
//        this.customerGroup = customerGroup;
//    }

    public SchduleTask() {
    }
}
