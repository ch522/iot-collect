package com.goldcard.iot.collect.protocol;

import com.goldcard.iot.collect.protocol.cmd.AbstractCmdResolve;
import com.goldcard.iot.collect.util.SpringContextHolder;

import java.util.List;

/**
 * 协议抽象类（组包,解包）
 *
 * @author 2005
 */
public abstract class AbstractProtocolResolve {

    /**
     * 协议号
     *
     * @author 2005
     */
    public abstract String protocolNo();

    /**
     * 协议解包
     *
     * @param bs 接收到的byte数组
     * @author 2005
     */
    public abstract UnifiedProtocol unPack(byte[] bs);

    /**
     * 协议组包
     *
     * @param inMap 需要组包的数据
     * @author 2005
     */
    public abstract byte[] pack(PackData inMap);


    protected <T extends AbstractCmdResolve> List<T> getCmds(Class<T> clazz) {
        return SpringContextHolder.getBeansWithType(clazz);
    }

//    /**
//     * 协议处理
//     *
//     * @param data 需要处理的数据
//     * @author 2005
//     */
//    public abstract void process(UnifiedProtocol data);

}
