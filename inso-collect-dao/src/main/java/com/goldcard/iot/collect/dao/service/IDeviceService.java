package com.goldcard.iot.collect.dao.service;


import com.goldcard.iot.collect.dao.model.pojo.Device;

/**
 * @Author G002005
 * @Date 2020/4/20 21:41
 */
public interface IDeviceService {

    Device findByNo(String no);

}
