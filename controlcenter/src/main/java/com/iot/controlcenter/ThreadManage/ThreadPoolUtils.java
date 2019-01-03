package com.iot.controlcenter.ThreadManage;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 * 线程池工具类
 */
public class ThreadPoolUtils {

    /**
     * 日志
     */
    public static Logger logger = Logger.getLogger(ThreadPoolUtils.class);

    /**
     * 存放当前的线程池
     */
    public static ExecutorService executorServiceNow;


    /**
     * 存放即将被销毁的线程
     */
    public static ExecutorService executorServiceOld;

    /**
     * 存放被销毁的线程池
     */
    public static List<ExecutorService> oldExecutorServiceList = new ArrayList<>();


    public static boolean isShutdownThread = false;


    /**
     * 停止线程池
     */
    public static void shutDown()   {
        if (executorServiceNow != null) {
            logger.info("shutDown()"+"开始停止线程池");
            executorServiceNow.shutdown();
            oldExecutorServiceList.add(executorServiceNow);
            executorServiceNow = null;
            //停止正在执行的线程
            isShutdownThread=true;
            logger.info("停止线程池成功");

        }
    }

    /**
     * 添加任务到线程池中
     * @param runnable
     */
    public static void addTaskToThreadPool(Runnable runnable){
        //让线程开始执行
        isShutdownThread=false;
        if(executorServiceNow==null){
            logger.info("addTaskToThreadPool()"+"创建新的线程池并添加任务");
            executorServiceNow= Executors.newCachedThreadPool();
            executorServiceNow.submit(runnable);
            logger.info("addTaskToThreadPool()"+"添加任务成功");
        }else{
            logger.info("addTaskToThreadPool()"+"在已有线程池中添加任务");
            executorServiceNow.submit(runnable);
            logger.info("addTaskToThreadPool()"+"添加任务成功");
        }
    }

}
