package com.iot.controlcenter.temp;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @author LHT
 */
public class Main implements Runnable {


    private static ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
    private Logger logger = Logger.getLogger(Main.class);
    private static ExecutorService executorService;
    private List<Runnable> list = null;

    @Override
    public void run() {
        synchronized (Main.class) {
            while (true) {
                logger.info(Thread.currentThread().getName() + "正在添加数据");
            }
        }
    }


    public static void main(String[] args) {

    }
}

