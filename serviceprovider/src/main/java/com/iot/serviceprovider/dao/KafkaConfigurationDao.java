package com.iot.serviceprovider.dao;

import com.iot.iservice.entity.po.KafkaConfigurationPO;


/**
 * 生产者和消费者配置信息的dao层
 */
public interface KafkaConfigurationDao {

    /**
     * 插入配置信息
     *
     * @param kafkaConfigurationPO
     * @return
     */
    int insertConfiguration(KafkaConfigurationPO kafkaConfigurationPO);

    /** 根据owner进行查询配置信息
     * @param owner
     * @return 返回配置信息
     */
    KafkaConfigurationPO selectConfigurationByOwner(String owner);
}


