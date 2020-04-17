package com.goldcard.iot.collect.protocol.cmd;


import com.goldcard.iot.collect.MessageBean;
import com.goldcard.protocol.InwardCommand;

/**
 * 协议命令处理抽象
 *
 * @author 2005
 */
public abstract class AbstractCmdHandler {
    /**
     * 协议号
     *
     * @author 2005
     */
    public abstract String protocolNo();

    /**
     * 指令码
     *
     * @author 2005
     */
    public abstract String cmdCode();

    /**
     * 处理
     *
     * @Author 2005
     * @Date 2020/2/3 下午4:34
     */
    public abstract void handler(MessageBean bean);
}
