package com.goldcard.iot.collect.protocol.handler.jk10.cmd.impl;

import com.goldcard.iot.collect.CmdSendPool;
import com.goldcard.iot.collect.IotConstants;
import com.goldcard.iot.collect.MessageBean;
import com.goldcard.iot.collect.cache.IotCacheManager;
import com.goldcard.iot.collect.observer.ISubject;
import com.goldcard.iot.collect.protocol.handler.biz.IDeviceBiz;
import com.goldcard.iot.collect.protocol.handler.jk10.cmd.AbstractCmdHandlerJk10;
import com.goldcard.iot.collect.protocol.handler.model.pojo.Device;
import com.goldcard.iot.collect.util.SpringContextHolder;
import com.goldcard.protocol.InwardCommand;
import com.goldcard.protocol.jk.jk16.outward.JK16_11FF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sun.plugin2.message.Message;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@Scope("prototype")
public class CmdHandlerJK10_1F00 extends AbstractCmdHandlerJk10 {
    @Override
    public String cmdCode() {
        return "1F00";
    }

    @Autowired
    private IDeviceBiz deviceBiz;
    @Autowired
    private IotCacheManager<String, LinkedBlockingQueue<CmdSendPool>> iotCacheManager;

    @Override
    public void handler(MessageBean item) {
        // TODO 命令具体处理
        Device device = deviceBiz.findByNo(item.getItem().getDeviceNo());
        // TODO 指令池添加数据

        JK16_11FF end = new JK16_11FF();
        end.setCurrentPrice(new BigDecimal("0"));
        end.setCurrentPrice(new BigDecimal("0"));
        end.setServerInfoMsg("00");
        end.setDestination(item.getItem().getDeviceNo());

        CmdSendPool pool = new CmdSendPool();
        pool.setHasEnd(Boolean.TRUE);
        pool.setOutwardCommand(end);
        pool.setSendDate(new Date());

        String cmdPoolId = IotConstants.IOT_CACHE_SOCKET_SHORT_CMD_KEY + item.getSessionId();
        LinkedBlockingQueue<CmdSendPool> queue = iotCacheManager.getCache().get(cmdPoolId);
        queue.offer(pool);
    }
}
