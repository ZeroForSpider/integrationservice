package com.iot.serviceprovider;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;


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
        return randomMac4Qemu()+" "+String.valueOf(System.currentTimeMillis()) ;
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

    }
}


