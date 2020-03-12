package com.goldcard.iot.collect.protocol.handler.jk10.cmd.impl;

import com.goldcard.iot.collect.IotConstants;
import com.goldcard.iot.collect.observer.ISubject;
import com.goldcard.iot.collect.protocol.UnifiedProtocol;
import com.goldcard.iot.collect.protocol.handler.jk10.cmd.AbstractCmdHandlerJk10;
import com.goldcard.iot.collect.util.SpringContextHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CmdHandlerJK10_1F00 extends AbstractCmdHandlerJk10 {
    @Override
    public String cmdCode() {
        return "1F00";
    }

    @Override
    public void handler(UnifiedProtocol item) {
        // TODO 命令具体处理

        // TODO 指令池添加数据




        //后续发送处理
        String observerId = IotConstants.IOT_CACHE_SOCKET_SHORT_OBSERVER_KEY + item.getSessionId();
        ISubject subject = SpringContextHolder.getBean(ISubject.class);
        subject.send(observerId);

    }
}
