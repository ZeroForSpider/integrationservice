package com.iot.controlcenter.config;

import java.util.*;

public class CommonConfig {
    /**
     * 生产者最大线程数
     */
  public   String  MaxThreadCountKeyOfProducer="";

    /**
     * 主题
     */
   public String TopicKey="";

    /**
     * 是否立即更新
     */
   public   String  IsUpdateKeyOfProducer;

    /**
     *  消费者最大线程数
     */
   public   String  MaxThreadCountKeyOfCustomer;

    /**
     * 是否立即不更新
     */
   public String IsUpdateKeyOfCustomer;


   public Properties properties;

    public CommonConfig(String a) {
        MaxThreadCountKeyOfProducer = "MaxThreadCountKeyOfProducer";
        TopicKey = "TopicKey";
        IsUpdateKeyOfProducer = "IsUpdateKeyOfProducer";
        MaxThreadCountKeyOfCustomer = "MaxThreadCountKeyOfCustomer";
        IsUpdateKeyOfCustomer = "IsUpdateKeyOfCustomer";
        this.properties=new Properties();
    }

    public CommonConfig() {
    }

    public void setMaxThreadCountKeyOfProducer(String maxThreadCountKeyOfProducer) {
        MaxThreadCountKeyOfProducer = maxThreadCountKeyOfProducer;
    }

    public void setTopicKey(String topicKey) {
        TopicKey = topicKey;
    }

    public void setIsUpdateKeyOfProducer(String isUpdateKeyOfProducer) {
        IsUpdateKeyOfProducer = isUpdateKeyOfProducer;
    }

    public void setMaxThreadCountKeyOfCustomer(String maxThreadCountKeyOfCustomer) {
        MaxThreadCountKeyOfCustomer = maxThreadCountKeyOfCustomer;
    }

    public void setIsUpdateKeyOfCustomer(String isUpdateKeyOfCustomer) {
        IsUpdateKeyOfCustomer = isUpdateKeyOfCustomer;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getMaxThreadCountKeyOfProducer() {
        return MaxThreadCountKeyOfProducer;
    }

    public String getTopicKey() {
        return TopicKey;
    }

    public String getIsUpdateKeyOfProducer() {
        return IsUpdateKeyOfProducer;
    }

    public String getMaxThreadCountKeyOfCustomer() {
        return MaxThreadCountKeyOfCustomer;
    }

    public String getIsUpdateKeyOfCustomer() {
        return IsUpdateKeyOfCustomer;
    }

    public Properties getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return "CommonConfig{" +
                "MaxThreadCountKeyOfProducer='" + MaxThreadCountKeyOfProducer + '\'' +
                ", TopicKey='" + TopicKey + '\'' +
                ", IsUpdateKeyOfProducer='" + IsUpdateKeyOfProducer + '\'' +
                ", MaxThreadCountKeyOfCustomer='" + MaxThreadCountKeyOfCustomer + '\'' +
                ", IsUpdateKeyOfCustomer='" + IsUpdateKeyOfCustomer + '\'' +
                ", properties=" + properties +
                '}';
    }
}
