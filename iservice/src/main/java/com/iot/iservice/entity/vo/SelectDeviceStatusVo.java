package com.iot.iservice.entity.vo;

import java.io.Serializable;
import java.util.List;

public class SelectDeviceStatusVo implements Serializable {

    public Integer deviceStatus;

    public List<String> macAddressList;


    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public void setMacAddressList(List<String> macAddressList) {
        this.macAddressList = macAddressList;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public List<String> getMacAddressList() {
        return macAddressList;
    }

    public SelectDeviceStatusVo(List<String> macAddressList) {
        this.macAddressList = macAddressList;
    }
}
