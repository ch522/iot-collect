package com.goldcard.iot.collect.protocol.cmd;


import com.goldcard.iot.collect.protocol.PackData;
import com.goldcard.iot.collect.protocol.UnifiedProtocol;

/**
 * 协议命令解析抽象类(具体命令的组包、解包)
 *
 * @author 2005
 */
public abstract class AbstractCmdResolve {

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
     * 解包
     *
     * @param bs 接收到的数据
     * @author 2005
     */
    public abstract UnifiedProtocol unPack(byte[] bs);

    /**
     * 组包
     *
     * @param inMap 需要组包的数据
     * @author 2005
     */
    public abstract byte[] pack(PackData inMap);

}
