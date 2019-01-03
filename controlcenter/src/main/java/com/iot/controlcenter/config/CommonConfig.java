package com.iot.controlcenter.config;

import java.util.*;

public class CommonConfig {
    /**
     * 生产者最大线程数
     */
  private    String  maxThreadCountOfProducer;

    /**
     * 主题
     */
    private String topic;

    /**
     * 是否立即更新
     */
    private   String  isUpdateOfProducer;

    /**
     *  消费者最大线程数
     */
    private   String  maxThreadCountOfCustomer;

    /**
     * 是否立即不更新
     */
    private String isUpdateOfCustomer;


    private Properties properties;

    public CommonConfig() {
        this.maxThreadCountOfProducer="3";
        this.topic="htl";
        this.isUpdateOfCustomer="1";
        this.maxThreadCountOfCustomer="3";
        this.isUpdateOfProducer="1";
        this.properties=new Properties();
    }


    public String getMaxThreadCountOfProducer() {
        return maxThreadCountOfProducer;
    }

    public String getTopic() {
        return topic;
    }

    public String getIsUpdateOfProducer() {
        return isUpdateOfProducer;
    }

    public String getMaxThreadCountOfCustomer() {
        return maxThreadCountOfCustomer;
    }

    public String getIsUpdateOfCustomer() {
        return isUpdateOfCustomer;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setMaxThreadCountOfProducer(String maxThreadCountOfProducer) {
        this.maxThreadCountOfProducer = maxThreadCountOfProducer;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setIsUpdateOfProducer(String isUpdateOfProducer) {
        this.isUpdateOfProducer = isUpdateOfProducer;
    }

    public void setMaxThreadCountOfCustomer(String maxThreadCountOfCustomer) {
        this.maxThreadCountOfCustomer = maxThreadCountOfCustomer;
    }

    public void setIsUpdateOfCustomer(String isUpdateOfCustomer) {
        this.isUpdateOfCustomer = isUpdateOfCustomer;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "CommonConfig{" +
                "maxThreadCountOfProducer='" + maxThreadCountOfProducer + '\'' +
                ", topic='" + topic + '\'' +
                ", isUpdateOfProducer='" + isUpdateOfProducer + '\'' +
                ", maxThreadCountOfCustomer='" + maxThreadCountOfCustomer + '\'' +
                ", isUpdateOfCustomer='" + isUpdateOfCustomer + '\'' +
                '}';
    }
}
