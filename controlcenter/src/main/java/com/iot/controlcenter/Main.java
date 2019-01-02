package com.iot.controlcenter;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LHT
 */
public class Main {
    public static void main(String[] args) {
        new Test().start();
        new Test().start();
        new Test().start();
        new Test().start();
    }
}

class Test extends Thread {
    private static ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
    private Logger logger = Logger.getLogger(Test.class);

    @Override
    public void run() {
        synchronized (Test.class) {
            for (int i = 0; i < 5; i++) {
                logger.info(Thread.currentThread().getName() + "正在添加数据" + i);
                concurrentHashMap.put(Thread.currentThread().getName(), i);
            }
        }
    }
}

class insertData {
    private Logger logger = Logger.getLogger(insertData.class);
    private ArrayList<String> arrayList = new ArrayList<>();

    public synchronized void add() {
        for (int i = 0; i < 5; i++) {
            logger.info(Thread.currentThread().getName() + "正在插入数据" + i);
            arrayList.add(String.valueOf(i));
        }
    }
}
