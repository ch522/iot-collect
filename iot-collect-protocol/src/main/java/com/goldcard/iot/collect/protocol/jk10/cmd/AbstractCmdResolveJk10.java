package com.goldcard.iot.collect.protocol.jk10.cmd;

import com.goldcard.iot.collect.protocol.cmd.AbstractCmdResolve;

/**
 * JK10解析命令抽象类
 *
 * @Author 2005
 * @Date 2020/2/3 下午4:41
 */
public abstract class AbstractCmdResolveJk10 extends AbstractCmdResolve {
    @Override
    public String protocolNo() {
        return "JK10";
    }

}
