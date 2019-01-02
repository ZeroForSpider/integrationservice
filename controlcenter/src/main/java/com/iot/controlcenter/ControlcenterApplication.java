package com.iot.controlcenter;

import com.iot.controlcenter.customerGroup.CustomerGroup;
import com.iot.controlcenter.producergroup.ProducerHandle;
import com.iot.iservice.entity.po.CustomerDeviceStatusPO;
import com.iot.iservice.entity.vo.UpDateDeviceStatusVo;
import com.iot.iservice.service.ICustomerDeviceStatus;
import com.iot.iservice.service.IKafkaConfiguration;
import org.apache.catalina.webresources.ClasspathURLStreamHandler;
import org.omg.CORBA.PRIVATE_MEMBER;
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


    @Autowired
    private IKafkaConfiguration iKafkaConfiguration;

    @Autowired
    private CustomerGroup customerGroup;


    @Autowired
    private ProducerHandle producerHandle;

    @Bean
    public RestTemplate restTemplate() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new TimeTask(iKafkaConfiguration,customerGroup),10000, 3000000, TimeUnit.MILLISECONDS);
        return builder.build();
    }


}

