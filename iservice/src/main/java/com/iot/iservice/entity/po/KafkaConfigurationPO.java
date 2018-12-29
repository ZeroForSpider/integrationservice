package com.iot.iservice.entity.po;

/**
 * 存取生产者和消费者的kafka配置信息的持久化实体类
     * 对应数据库中的 kafka_configuration_table 表
 */
public class KafkaConfigurationPO {

    /**
     * id 主键
     */
    private Integer id;

    /**
     * 配置信息
     */
    private String configuration;

    /**
     * 持有者
     */
    private String owner;


    public Integer getId() {
        return id;
    }

    public String getConfiguration() {
        return configuration;
    }

    public String getOwner() {
        return owner;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
