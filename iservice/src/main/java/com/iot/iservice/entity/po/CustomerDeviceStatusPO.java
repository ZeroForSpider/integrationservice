package com.iot.iservice.entity.po;

import java.io.Serializable;

public class CustomerDeviceStatusPO implements Serializable {

    private Integer id;

    private String  deviceMacAddress;

    private String  generatorTimeStamp;

    private Integer deviceStatus;

    public CustomerDeviceStatusPO() {
    }

    public CustomerDeviceStatusPO(String deviceMacAddress, String generatorTimeStamp, Integer deviceStatus) {
        this.deviceMacAddress = deviceMacAddress;
        this.generatorTimeStamp = generatorTimeStamp;
        this.deviceStatus = deviceStatus;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDeviceMacAddress(String deviceMacAddress) {
        this.deviceMacAddress = deviceMacAddress;
    }

    public void setGeneratorTimeStamp(String generatorTimeStamp) {
        this.generatorTimeStamp = generatorTimeStamp;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public Integer getId() {
        return id;
    }

    public String getDeviceMacAddress() {
        return deviceMacAddress;
    }

    public String getGeneratorTimeStamp() {
        return generatorTimeStamp;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    /**
     * 重写equal方法
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return this.deviceMacAddress.equals(((CustomerDeviceStatusPO)obj).getDeviceMacAddress())?true:false;
    }
}
