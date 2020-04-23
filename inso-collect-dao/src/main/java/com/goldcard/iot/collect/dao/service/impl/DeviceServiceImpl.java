package com.goldcard.iot.collect.dao.service.impl;

import com.goldcard.iot.collect.dao.biz.IDeviceBiz;
import com.goldcard.iot.collect.dao.model.bo.DeviceConditionBo;
import com.goldcard.iot.collect.dao.model.pojo.Device;
import com.goldcard.iot.collect.dao.service.IDeviceService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @Author G002005
 * @Date 2020/4/20 21:41
 */
@Service
public class DeviceServiceImpl implements IDeviceService {
    @Autowired
    private IDeviceBiz deviceBiz;

    @Override
    @Transactional(readOnly = true)
    public Device findByNo(String no) {
        return deviceBiz.findByNo(no);
    }
}
