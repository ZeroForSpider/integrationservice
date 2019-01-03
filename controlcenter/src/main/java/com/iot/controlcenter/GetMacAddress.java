package com.iot.controlcenter;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GetMacAddress {
    public static Lock lock = new ReentrantLock();

    public static String randMacAddress() {
        lock.lock();
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
        lock.unlock();
        return String.join(SEPARATOR_OF_MAC, mac);
    }

}
