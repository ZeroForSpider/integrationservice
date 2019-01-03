package com.iot.controlcenter.ThreadManage;

import com.iot.controlcenter.MacAndLock;
import com.iot.controlcenter.model.Message;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

public class GeneratorTimeStamp implements Runnable {

    /**
     * 日志
     */
    public static Logger logger = Logger.getLogger(GeneratorTimeStamp.class);

    private MacAndLock macAndLock;

    public GeneratorTimeStamp(MacAndLock macAndLock) {
        this.macAndLock = macAndLock;
    }

    @Override
    public void run() {
        macAndLock.getLock().lock();
        StringBuilder currentKey = new StringBuilder(macAndLock.getMacAddress());
        logger.info(Thread.currentThread().getName() + "获得了" + currentKey + "的锁");
        DataOperator.messageConcurrentHashMap.put(
                currentKey.toString(),
                new Message(
                        currentKey.toString(),
                        currentKey.hashCode() % 2 == 0 ? 1 : 0,
                        String.valueOf(System.currentTimeMillis())
                )
        );
        logger.info(Thread.currentThread().getName() + "  " + DataOperator.messageConcurrentHashMap.get(macAndLock.getMacAddress()));
        DataOperator.lockConcurrentHashMap.remove(macAndLock.getMacAddress());
        macAndLock.getLock().unlock();
    }
}
