package com.goldcard.iot.collect.protocol.handler.biz.impl;

import com.goldcard.iot.collect.protocol.handler.biz.IDeviceBiz;
import com.goldcard.iot.collect.protocol.handler.dao.impl.BaseDaoImpl;
import com.goldcard.iot.collect.protocol.handler.model.bo.DeviceConditionBo;
import com.goldcard.iot.collect.protocol.handler.model.pojo.Device;
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