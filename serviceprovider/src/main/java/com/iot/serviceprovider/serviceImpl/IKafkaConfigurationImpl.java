package com.iot.serviceprovider.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.iot.iservice.entity.po.KafkaConfigurationPO;
import com.iot.iservice.service.IKafkaConfiguration;
import com.iot.serviceprovider.dao.KafkaConfigurationDao;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取生产者和消费者配置的接口实现
 */

@Service(version = "1.0.0")
public class IKafkaConfigurationImpl implements IKafkaConfiguration {

    /**
     * 生产者和消费者的去单位配置DAO层
     */
    @Resource
    private KafkaConfigurationDao kafkaConfigurationDao;

    /**
     * 日志
     */
    private Logger logger=Logger.getLogger(IKafkaConfigurationImpl.class);

    /**
     * 获取消费者配置的实现
     *
     * @return
     */
    @Override
    public Map<String, String> getCustomerConfiguration() {
        logger.info("读取消费者配置");
        Map map=new HashMap<>();
        KafkaConfigurationPO kafkaConfigurationPO = kafkaConfigurationDao.selectConfigurationByOwner(IKafkaConfiguration.customerOwnerKey);
        logger.info("读取的配置信息为: "+kafkaConfigurationPO.toString());
        if(kafkaConfigurationPO!=null){
            map= JSON.parseObject(kafkaConfigurationPO.getConfiguration());
        }
        return map;
    }

    /**
     * 获取生产者配置的实现
     *
     * @return
     */

    @Override
    public Map<String, String> getProducerConfiguration() {
        logger.info("读取生产者配置信息");
        Map map=new HashMap();
        KafkaConfigurationPO kafkaConfigurationPO=kafkaConfigurationDao.selectConfigurationByOwner(IKafkaConfiguration.producerOwnerKey);
        logger.info("读取的生产者配置信息为： "+kafkaConfigurationPO.toString());
        if(kafkaConfigurationPO!=null){
            map= JSON.parseObject(kafkaConfigurationPO.getConfiguration());
        }
        return map;
    }
}
