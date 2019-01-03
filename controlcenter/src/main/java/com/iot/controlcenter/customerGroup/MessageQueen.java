package com.iot.controlcenter.customerGroup;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 消费者缓存数据的消息队列
 * @author LHT
 */
public class MessageQueen {

    /**
     * 缓存消息的并发消息队列
     */
    private static ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    /**
     *添加消息到消息队列
     * @param message 消息
     */
    public static void addMessageToQueen(String message){
        concurrentLinkedQueue.add(message);
    }

    /**
     * 从队列中获取消息，获取后并从队列中移除该消息
     * @return
     */
    public static String getMessageFromQueen(){
        return concurrentLinkedQueue.poll();
    }

    /**
     * 获取当前消息队列的长度
     * @return
     */
    public static Integer getMessageQueenSize(){
        return concurrentLinkedQueue.size();
    }

}
