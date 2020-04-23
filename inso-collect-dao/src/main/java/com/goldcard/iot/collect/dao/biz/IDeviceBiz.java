package com.goldcard.iot.collect.dao.biz;


import com.goldcard.iot.collect.dao.base.IBaseDao;
import com.goldcard.iot.collect.dao.model.pojo.Device;

public interface IDeviceBiz extends IBaseDao<Device> {

    Device findByNo(String no);
}