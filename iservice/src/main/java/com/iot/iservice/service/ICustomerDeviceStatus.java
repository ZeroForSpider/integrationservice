package com.iot.iservice.service;

import com.iot.iservice.entity.po.CustomerDeviceStatusPO;
import com.iot.iservice.entity.vo.SelectDeviceStatusVo;
import com.iot.iservice.entity.vo.UpDateDeviceStatusVo;

import java.util.List;

/**
 * 生产者对设备信息的读写接口
 */
public interface ICustomerDeviceStatus{

     int insertBatchDeviceStatus(List<CustomerDeviceStatusPO> list);


     int updateBatchDeviceStatus(UpDateDeviceStatusVo upDateDeviceStatusVo);


     List<CustomerDeviceStatusPO> selectDeviceInfoByMacAddress(SelectDeviceStatusVo selectDeviceStatusVo);
}
