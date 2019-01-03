package com.iot.controlcenter.ThreadManage;

import com.iot.controlcenter.model.Message;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DataOperator {

    public static ConcurrentHashMap<String, Lock> lockConcurrentHashMap = new ConcurrentHashMap<>();


    public static ConcurrentHashMap<String, Message> messageConcurrentHashMap = new ConcurrentHashMap<>();

    public static String randMacAddress() {
        String SEPARATOR_OF_MAC = ":";
        Random random = new Random();
        String[] mac = {
                String.format("%02x", 0x52),
                String.format("%02x", 0x54),
                String.format("%02x", 0x00),
                String.format("%02x", random.nextInt(0xFF)),
                String.format("%02x", random.nextInt(0xFF)),
                String.format("%02x", random.nextInt(0xFF))
        };
        return String.join(SEPARATOR_OF_MAC, mac);
    }

    public static void generatorMacAddress() {
        for (int i = 0; i < 1000; i++) {
            lockConcurrentHashMap.put(randMacAddress(), new ReentrantLock());
        }
    }

}
