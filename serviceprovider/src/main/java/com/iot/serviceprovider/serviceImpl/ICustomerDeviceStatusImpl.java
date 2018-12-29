package com.iot.serviceprovider.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.iot.iservice.entity.po.CustomerDeviceStatusPO;
import com.iot.iservice.entity.vo.SelectDeviceStatusVo;
import com.iot.iservice.entity.vo.UpDateDeviceStatusVo;
import com.iot.iservice.service.ICustomerDeviceStatus;
import com.iot.serviceprovider.dao.CustomerDeviceStatusDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "1.0.0")
public class ICustomerDeviceStatusImpl implements ICustomerDeviceStatus {

    @Autowired
    private CustomerDeviceStatusDao customerDeviceStatusDao;

    @Override
    public int insertBatchDeviceStatus(List<CustomerDeviceStatusPO> list) {
        return customerDeviceStatusDao.insertBatchDeviceStatus(list);
    }

    @Override
    public int updateBatchDeviceStatus(UpDateDeviceStatusVo upDateDeviceStatusVo) {
        return customerDeviceStatusDao.updateBatchDeviceStatus(upDateDeviceStatusVo);
    }

    @Override
    public List<CustomerDeviceStatusPO> selectDeviceInfoByMacAddress(SelectDeviceStatusVo selectDeviceStatusVo) {
        return customerDeviceStatusDao.selectDeviceInfoByMacAddress(selectDeviceStatusVo);
    }
}
