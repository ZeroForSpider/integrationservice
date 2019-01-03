package com.iot.controlcenter.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.iot.controlcenter.ThreadManage.ThreadPoolUtils;
import com.iot.controlcenter.customerGroup.CustomerHandle;
import com.iot.controlcenter.customerGroup.MessageHandle;
import com.iot.controlcenter.producergroup.ProducerHandle;
import com.iot.iservice.service.ICustomerDeviceStatus;
import com.iot.iservice.service.IKafkaConfiguration;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * 定时检查kafka配置信息是否发生变化
 *
 * @author LHT
 */
public class CheckKafkaConfigurationIsUpdate implements Runnable {

    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(CheckKafkaConfigurationIsUpdate.class);

    /**
     * 获取配置信息的接口
     */
    private IKafkaConfiguration iKafkaConfiguration;

    /**
     * 获取设备信息接口
     */
    private ICustomerDeviceStatus iCustomerDeviceStatus;

    /**
     * 存放上一次的消费者配置信息
     */
    private static String oldConsumerConfiguration = "";

    /**
     * 存放上一次的生产都配置信息
     */
    private static String oldProducerConfiguration = "";

    /**
     * 消费者的启动次数
     */
    private static Integer startCountOfCustomer = 0;

    /**
     * 生产都的启动次数
     */
    private static Integer startCountOfProducer = 0;

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
            commonConfig.setMaxThreadCountOfCustomer(jsonObject.get("maxThreadCountOfCustomer").toString());
            commonConfig.setTopic(jsonObject.get("topic").toString());
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
            commonConfig.setMaxThreadCountOfProducer(jsonObject.get("maxThreadCountOfProducer").toString());
            commonConfig.setTopic(jsonObject.get("topic").toString());
            JSONArray jsonArray = jsonObject.getJSONArray("kafkaproperties");
            Map<String, String> map = JSON.parseObject(jsonArray.toJSONString(), new TypeReference<Map<String, String>>() {
            });
            for (Map.Entry<String, String> entry : map.entrySet()) {
                commonConfig.getProperties().put(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            logger.info(e);
        }
        logger.info("生产者配置信息" + commonConfig);
        return commonConfig;
    }

    public CheckKafkaConfigurationIsUpdate(IKafkaConfiguration iKafkaConfiguration, ICustomerDeviceStatus iCustomerDeviceStatus) {
        this.iKafkaConfiguration = iKafkaConfiguration;
        this.iCustomerDeviceStatus = iCustomerDeviceStatus;
    }

    @Override
    public void run() {
        //消费者配置信息有变动
        if (!iKafkaConfiguration.getCustomerConfiguration().equals(oldConsumerConfiguration)) {
            oldConsumerConfiguration = iKafkaConfiguration.getCustomerConfiguration();
            CommonConfig customerConfig = getCustomerProperties();
            if (startCountOfCustomer != 0) {
                logger.info("停止线程池");
                ThreadPoolUtils.shutDown();
                try {
                    //让当前程序暂停10秒中，确保线程停止运行
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    logger.info("停止线程池发生异常" + e);
                }
            }
            for (int i = 0; i < Integer.valueOf(customerConfig.getMaxThreadCountOfCustomer()); i++) {
                logger.info("定时任务添加新的消费者到线程池");
                ThreadPoolUtils.addTaskToThreadPool(new CustomerHandle(customerConfig));
            }
            //将处理缓存消息队列的消息添加到线程池
            logger.info("定时将处理缓存消息队列的消息添加到线程池");
            ThreadPoolUtils.addTaskToThreadPool(new MessageHandle(iCustomerDeviceStatus));
            startCountOfCustomer++;
        } else {
            startCountOfCustomer++;
            logger.info("定时检查：消费者配置信息无变化");
        }


        //生产者配置信息有变动
        if (!iKafkaConfiguration.getProducerConfiguration().equals(oldProducerConfiguration)) {
            oldProducerConfiguration = iKafkaConfiguration.getProducerConfiguration();
            CommonConfig producerCommonConfig = getProducerProperties();
            if (startCountOfProducer != 0) {
                logger.info("停止线程池");
                ThreadPoolUtils.shutDown();
                try {
                    //让当前程序暂停10秒中，确保线程停止运行
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < Integer.valueOf(producerCommonConfig.getMaxThreadCountOfProducer()); i++) {
                logger.info("定时任务添加新的生产者到线程池");
                ThreadPoolUtils.addTaskToThreadPool(new ProducerHandle(producerCommonConfig));
            }
            startCountOfProducer++;
        } else {
            startCountOfProducer++;
            logger.info("定时检查：生产者配置信息无变化");
        }
    }
}
