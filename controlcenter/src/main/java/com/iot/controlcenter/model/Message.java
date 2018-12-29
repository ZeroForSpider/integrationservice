package com.iot.controlcenter.model;

/**
 * 生产者和消费者接受消息的格式
 */
public class Message {

    /**
     * 设备地址
     */
    public String deviceMacAddress;

    /**
     * 设备状态
     */
    public Integer deviceStatus;

    /**
     * 设备生产时的时间戳
     */

    public String generatorTimestamp;


    public Message(String deviceMacAddress, Integer deviceStatus, String generatorTimestamp) {
        this.deviceMacAddress = deviceMacAddress;
        this.deviceStatus = deviceStatus;
        this.generatorTimestamp = generatorTimestamp;
    }

    public void setDeviceMacAddress(String deviceMacAddress) {
        this.deviceMacAddress = deviceMacAddress;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public void setGeneratorTimestamp(String generatorTimestamp) {
        this.generatorTimestamp = generatorTimestamp;
    }

    public String getDeviceMacAddress() {
        return deviceMacAddress;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public String getGeneratorTimestamp() {
        return generatorTimestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "deviceMacAddress='" + deviceMacAddress + '\'' +
                ", deviceStatus=" + deviceStatus +
                ", generatorTimestamp='" + generatorTimestamp + '\'' +
                '}';
    }
}
