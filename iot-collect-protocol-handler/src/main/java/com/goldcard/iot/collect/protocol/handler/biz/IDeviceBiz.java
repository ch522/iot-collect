package com.goldcard.iot.collect.protocol.handler.biz;

import com.goldcard.iot.collect.protocol.handler.dao.IBaseDao;
import com.goldcard.iot.collect.protocol.handler.model.pojo.Device;

public interface IDeviceBiz extends IBaseDao<Device> {

    Device findByNo(String no);
}