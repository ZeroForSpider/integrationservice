package com.iot.serviceprovider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.type.ReferenceType;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.BiConsumer;


class Producer1 implements Runnable {

    public static String randomMac4Qemu() {
        String SEPARATOR_OF_MAC = ":";
        Random random = new Random();
        String[] mac = {
                String.format("%02x", 0x52),
                String.format("%02x", 0x54),
                String.format("%02x", 0x00),
                String.format("%02x", random.nextInt(0x01)),
                String.format("%02x", random.nextInt(0x02)),
                String.format("%02x", random.nextInt(0x01))
        };
        return String.join(SEPARATOR_OF_MAC, mac);
    }

    public synchronized String getValue() throws InterruptedException {
        Thread.sleep(10);
        return randomMac4Qemu() + " " + String.valueOf(System.currentTimeMillis());
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                System.out.println(getValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException, BrokenBarrierException {
//        String jsonString = "{\"name\": \"all\",\t\"age\": \"0\",\"sex\": \"1\",\"topic\":\"基本电荷\"}";
//        JSONObject jsonObject = JSONObject.parseObject(jsonString);
//        System.out.println("jsonString:" + jsonString);
//        EvioConfig config = JSONObject.toJavaObject(jsonObject, EvioConfig.class);
//        System.out.println(config);
//        System.out.println("config.topic= " + config.getTopic());

        String jsonString="{\n" +
                "\t\"maxThreadCountOfProducer\": \"3\",\n" +
                "\t\"topic\": \"htl\",\n" +
                "\t\"isUpdateOfCustomer\": \"1\",\n" +
                "\t\"maxThreadCountOfCustomer\": \"3\",\n" +
                "\t\"isUpdateOfProducer\": \"1\",\n" +
                "\t\"kafkaproperties\": [{\n" +
                "\t\t\"group.id\": \"group-1\",\n" +
                "\t\t\"key.deserializer\": \"org.apache.kafka.common.serialization.StringDeserializer\",\n" +
                "\t\t\"auto.offset.reset\": \"earliest\",\n" +
                "\t\t\"bootstrap.servers\": \"119.29.193.187:9092\",\n" +
                "\t\t\"enable.auto.commit\": \"true\",\n" +
                "\t\t\"session.timeout.ms\": \"30000\",\n" +
                "\t\t\"value.deserializer\": \"org.apache.kafka.common.serialization.StringDeserializer\",\n" +
                "\t\t\"auto.commit.interval.ms\": \"1000\"\n" +
                "\t}]\n" +
                "}";
        JSONObject jsonObject= JSONObject.parseObject(jsonString);
        JSONArray jsonArray=jsonObject.getJSONArray("kafkaproperties");
        Map<String,String> map=JSON.parseObject(jsonArray.toJSONString(),new TypeReference<Map<String,String>>(){});
        for(Map.Entry<String,String> entry:map.entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }
}


