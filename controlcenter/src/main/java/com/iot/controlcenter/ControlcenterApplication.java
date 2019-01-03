package com.iot.controlcenter;

import com.iot.controlcenter.config.CheckKafkaConfigurationIsUpdate;
import com.iot.controlcenter.producergroup.ProducerHandle;
import com.iot.iservice.service.ICustomerDeviceStatus;
import com.iot.iservice.service.IKafkaConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@ImportResource(value = {"classpath:dubbo-customer.xml"})
public class ControlcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ControlcenterApplication.class, args);
    }

    @Autowired
    private RestTemplateBuilder builder;


    /**
     * kafka配置信息接口
     */
    @Autowired
    private IKafkaConfiguration iKafkaConfiguration;


    /**
     * 设备信息配置接口
     */
    @Autowired
    private ICustomerDeviceStatus iCustomerDeviceStatus;

    @Bean
    public RestTemplate restTemplate() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new CheckKafkaConfigurationIsUpdate(iKafkaConfiguration, iCustomerDeviceStatus), 10000, 10000, TimeUnit.MILLISECONDS);
        return builder.build();
    }
}

