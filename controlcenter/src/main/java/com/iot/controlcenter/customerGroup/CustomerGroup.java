package com.iot.controlcenter.customerGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iot.controlcenter.config.CommonConfig;
import com.iot.controlcenter.model.Message;
import com.iot.iservice.entity.po.CustomerDeviceStatusPO;
import com.iot.iservice.entity.vo.SelectDeviceStatusVo;
import com.iot.iservice.entity.vo.UpDateDeviceStatusVo;
import com.iot.iservice.service.ICustomerDeviceStatus;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 消费者工作组
 */
@Component
public class CustomerGroup {

    /**
     * 存放从kafka拿到的消息，交给后续的线程处理
     */
    public static ConcurrentLinkedDeque<String> messageQueue = new ConcurrentLinkedDeque<>();


    public Logger logger = Logger.getLogger(CustomerGroup.class);

    @Autowired
    public ICustomerDeviceStatus iCustomerDeviceStatus;

    /**
     * 消费者处理类
     */
    class CustomerHandle implements Runnable {

        /**
         * 配置信息
         */
        private CommonConfig commonConfig;

        @Override
        public void run() {
            try {
                logger.info("RUN");
                logger.info("confdig  " + commonConfig.properties);
                KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(commonConfig.properties);
                logger.info("Keyy: " + commonConfig.TopicKey);
                kafkaConsumer.subscribe(Arrays.asList(commonConfig.TopicKey));
                while (true) {
                    logger.info("轮询消息");
                    ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
                    for (ConsumerRecord<String, String> record : records) {
//                        logger.info("接受消息");
//                        logger.info(record.value());
                        messageQueue.add(record.value());
                        logger.info("添加数据后的消息队列长度" + messageQueue.size());
                    }
                   Thread.sleep(1000);
                }
            } catch (Exception e) {
                logger.info("Exception " + e);
            }
        }


        public CustomerHandle(CommonConfig commonConfig) {
            this.commonConfig = commonConfig;
        }
    }

    /**
     * 消息处理类
     */
    class MessageHandle implements Runnable {

//        @Autowired
//        private ICustomerDeviceStatus iCustomerDeviceStatus;

        /**
         * 找到时间戳最大的消息
         *
         * @param messageList 返回list
         * @return
         */
        public List<Message> getlatestMessage(List<Message> messageList) {
            //将设备信息放到map中，用于对相同mac地址的设备信息进行分组
            Map<String, List<Message>> map = new HashMap<>();
            for (Message message : messageList) {
                map.put(message.getDeviceMacAddress(), null);
            }

            //将相同组的消息放到同一个list里
            for (Map.Entry<String, List<Message>> entry : map.entrySet()) {
                List<Message> list = new ArrayList<>();
                String key = entry.getKey();
                for (Message message : messageList) {
                    if (key.equals(message.getDeviceMacAddress())) {
                        list.add(message);
                    }
                }
                map.put(key, list);
            }

            //存放最新消息的结果
            List<Message> latestMessageList = new ArrayList<>();
            //将分组后的消息按时间戳进行排序
            for (Map.Entry<String, List<Message>> entry : map.entrySet()) {
                Collections.sort(entry.getValue(), new Comparator<Message>() {
                    @Override
                    public int compare(Message o1, Message o2) {
                        long timeStampOne = Long.valueOf(o1.getGeneratorTimestamp());
                        long timeStampTwo = Long.valueOf(o2.getGeneratorTimestamp());
                        if (timeStampOne < timeStampTwo) {
                            return 1;
                        }
                        if (timeStampOne == timeStampTwo) {
                            return 0;
                        }
                        return -1;
                    }
                });
                //放入每个设备的最新消息
                if (entry.getValue().size() > 0) {
                    latestMessageList.add(entry.getValue().get(0));
                }
            }
            return latestMessageList;
        }

        /**
         * 保存最新消息
         *
         * @param messageList 从kafka拿到的消息
         */
        public void saveMessage(List<Message> messageList) {
            List<Message> latestMessageList = getlatestMessage(messageList);
            //类型转换
            List<CustomerDeviceStatusPO> customerDeviceStatusPOList = new ArrayList<>();
            List<String> macAddressList = new ArrayList<>();

            //数据库中已经存在的记录
            List<CustomerDeviceStatusPO> existInDb = new ArrayList<>();
            //需要插入的设备信息
            List<CustomerDeviceStatusPO> insertDeviceList = new ArrayList<>();

            //需要将设备的状态信息更新为0
            List<CustomerDeviceStatusPO> updateDeviceStatusZeroList = new ArrayList<>();

            //需要将设备的状态信息更新为1
            List<CustomerDeviceStatusPO> updateDeviceStatusOneList = new ArrayList<>();
            for (Message message : latestMessageList) {
                macAddressList.add(message.getDeviceMacAddress());
                customerDeviceStatusPOList.add(new CustomerDeviceStatusPO(
                        message.getDeviceMacAddress(),
                        message.getGeneratorTimestamp(),
                        message.getDeviceStatus()
                ));
            }
            existInDb = iCustomerDeviceStatus.selectDeviceInfoByMacAddress(new SelectDeviceStatusVo(macAddressList));
            if (customerDeviceStatusPOList.size() > 0) {
                for (CustomerDeviceStatusPO customerDeviceStatusPO : customerDeviceStatusPOList) {
                    if (existInDb.contains(customerDeviceStatusPO)) {
                        //数据库中已有该设备信息就放入更新列表
                        if (customerDeviceStatusPO.getDeviceStatus() == 0) {
                            updateDeviceStatusZeroList.add(customerDeviceStatusPO);
                        } else {
                            updateDeviceStatusOneList.add(customerDeviceStatusPO);
                        }
                    } else {
                        //数据库中没有该设备信息就放入插入列表
                        insertDeviceList.add(customerDeviceStatusPO);
                    }
                }
            }
            //将设备的状态信息更新为0
            if (updateDeviceStatusZeroList.size() != 0) {
                iCustomerDeviceStatus.updateBatchDeviceStatus(new UpDateDeviceStatusVo(
                        0,
                        updateDeviceStatusZeroList
                ));
            }
            //将设备的状态信息更新为1
            if (updateDeviceStatusOneList.size() != 0) {
                iCustomerDeviceStatus.updateBatchDeviceStatus(new UpDateDeviceStatusVo(
                        1,
                        updateDeviceStatusOneList
                ));
            }
            //将设备信息存入数据库
            if (insertDeviceList.size() != 0) {
                iCustomerDeviceStatus.insertBatchDeviceStatus(insertDeviceList);
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    logger.info("messageQueue：" + messageQueue.size());
                    if (messageQueue.size() > 10) {
                        List<Message> list = new ArrayList<>();
                        logger.info("保存数据开始");
                        for (int i = 0; i < 10; i++) {
                            JSONObject jsonObject = JSON.parseObject(messageQueue.poll());
                            Message message = jsonObject.toJavaObject(Message.class);
                            list.add(message);
                        }
                        saveMessage(list);
                    } else {
                        Thread.sleep(1000);
                    }
                }
            } catch (Exception e) {
                logger.info("消息队列保存数据异常" + e);
            }
        }
    }

    /**
     * 创建消费者线程池
     *
     * @param threadNumber 线程个数
     * @param commonConfig 消费者配置信息
     * @param isShutdown   是否结束线程池
     */
    public void CreateThread(int threadNumber, CommonConfig commonConfig, boolean isShutdown) {
        logger.info("创建消费者线程池");
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        if (isShutdown) {
            executorService.shutdownNow();
        } else {
            try {

                executorService.submit(new MessageHandle());
            } catch (Exception e) {
                logger.info(e);
            }
            for (int i = 0; i < threadNumber-2; i++) {
                executorService.submit(new CustomerHandle(commonConfig));
            }
        }
    }

}