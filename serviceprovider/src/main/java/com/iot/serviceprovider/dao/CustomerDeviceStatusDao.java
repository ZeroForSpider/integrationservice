package com.iot.serviceprovider.dao;

import com.iot.iservice.entity.po.CustomerDeviceStatusPO;
import com.iot.iservice.entity.vo.SelectDeviceStatusVo;
import com.iot.iservice.entity.vo.UpDateDeviceStatusVo;

import java.util.List;

public interface CustomerDeviceStatusDao {

     int insertBatchDeviceStatus(List<CustomerDeviceStatusPO> list);

     int updateBatchDeviceStatus(UpDateDeviceStatusVo upDateDeviceStatusVo);

     List<CustomerDeviceStatusPO> selectDeviceInfoByMacAddress(SelectDeviceStatusVo selectDeviceStatusVo);

}
