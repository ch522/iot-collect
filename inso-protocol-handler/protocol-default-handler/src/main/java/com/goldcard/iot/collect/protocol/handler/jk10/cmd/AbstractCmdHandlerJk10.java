package com.goldcard.iot.collect.protocol.handler.jk10.cmd;

import com.goldcard.iot.collect.protocol.cmd.AbstractCmdHandler;

/**
 * jk10命令处理抽象类
 *
 * @Author 2005
 * @Date 2020/2/3 下午4:44
 */
public abstract class AbstractCmdHandlerJk10 extends AbstractCmdHandler {
    @Override
    public String protocolNo() {
        return "JK10";
    }
}
