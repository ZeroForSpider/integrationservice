package com.iot.iservice.entity.vo;

import com.iot.iservice.entity.po.CustomerDeviceStatusPO;

import java.io.Serializable;
import java.util.List;

public class UpDateDeviceStatusVo implements Serializable {

    public Integer deviceStatus;


    public List<CustomerDeviceStatusPO> customerDeviceStatusPOList;

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public void setList(List<CustomerDeviceStatusPO> list) {
        this.customerDeviceStatusPOList = list;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public List<CustomerDeviceStatusPO> getList() {
        return customerDeviceStatusPOList;
    }

    public UpDateDeviceStatusVo(Integer deviceStatus, List<CustomerDeviceStatusPO> list) {
        this.deviceStatus = deviceStatus;
        this.customerDeviceStatusPOList = list;
    }
}
