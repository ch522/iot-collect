package com.goldcard.iot.collect.protocol;

import com.goldcard.iot.collect.protocol.cmd.AbstractCmdHandler;
import com.goldcard.iot.collect.util.SpringContextHolder;

import java.util.List;

/**
 * 协议处理抽象类
 *
 * @Author 2005
 * @Date 2020/2/3 下午1:04
 */
public abstract class AbstractProtocolHandler {

    public abstract String protocolNo();

    /**
     * 处理
     *
     * @Author 2005
     * @Date 2020/2/3 下午1:31
     */
    public abstract void handler(UnifiedProtocol item);

    protected <T extends AbstractCmdHandler> List<T> getCmds(Class<T> clazz) {
        return SpringContextHolder.getBeansWithType(clazz);
    }
}
