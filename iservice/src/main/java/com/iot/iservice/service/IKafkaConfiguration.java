package com.iot.iservice.service;

import java.util.Map;

/**
 * 获取生产者和消费者的配置信息接口
 */
public interface IKafkaConfiguration {
    /**
     * 生产者最大线程数
     */
    String  MaxThreadCountKeyOfProducer="";

    /**
     * 主题
     */
    String TopicKey="";

    /**
     * 是否立即更新
     */
    boolean IsUpdateKeyOfProducer=false;

    /**
     *  消费者最大线程数
     */
    String  MaxThreadCountKeyOfCustomer="";

    /**
     * 是否立即不更新
     */
    boolean IsUpdateKeyOfCustomer=false;

    /**
     * 生产者的标识key
     */
     String producerOwnerKey="producer";

    /**
     * 消费者的标识key
     */
    String customerOwnerKey="customer";

    /**
     * 获取生产者配置信息接口
     * @return
     */
    Map<String,String> getProducerConfiguration();

    /**
     * 获取消费者配置信息接口
     * @return
     */
    Map<String,String> getCustomerConfiguration();

}
