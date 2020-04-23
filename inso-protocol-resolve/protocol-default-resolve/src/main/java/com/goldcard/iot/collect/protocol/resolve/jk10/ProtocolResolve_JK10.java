package com.goldcard.iot.collect.protocol.resolve.jk10;

import com.goldcard.ProtocolResolve;
import com.goldcard.iot.collect.protocol.AbstractProtocolResolve;
import com.goldcard.protocol.InwardCommand;
import com.goldcard.protocol.jk.jk16.JK16Protocol;
import io.netty.channel.embedded.EmbeddedChannel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * JK10协议解析
 *
 * @Author 2005
 * @Date 2020/2/3 下午4:50
 */
@Component
@Scope("prototype")
public class ProtocolResolve_JK10 extends AbstractProtocolResolve {
    @Override
    public String protocolNo() {
        return "JK10";
    }

    @Override
    public InwardCommand unPack(byte[] bs) {
        InwardCommand
                command = (InwardCommand) ProtocolResolve.bytes2Object(bs, new JK16Protocol(), new EmbeddedChannel());
        return command;
    }

}
