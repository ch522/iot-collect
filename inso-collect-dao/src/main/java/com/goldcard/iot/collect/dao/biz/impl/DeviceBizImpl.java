package com.goldcard.iot.collect.dao.biz.impl;


import com.goldcard.iot.collect.dao.base.impl.BaseDaoImpl;
import com.goldcard.iot.collect.dao.biz.IDeviceBiz;
import com.goldcard.iot.collect.dao.model.bo.DeviceConditionBo;
import com.goldcard.iot.collect.dao.model.pojo.Device;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeviceBizImpl extends BaseDaoImpl<Device> implements IDeviceBiz {
    @Override
    public Device findByNo(String no) {

        DeviceConditionBo condition = new DeviceConditionBo() {{
            setNo(no);
        }};
        List<Device> list = query(condition);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }

        return null;
    }
}