package com.goldcard.iot.collect.protocol.jk10.cmd.impl;

import com.goldcard.iot.collect.protocol.PackData;
import com.goldcard.iot.collect.protocol.UnifiedProtocol;
import com.goldcard.iot.collect.protocol.jk10.cmd.AbstractCmdResolveJk10;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CmdResolveJK10_1F00 extends AbstractCmdResolveJk10 {
    @Override
    public String cmdCode() {
        return "1F00";
    }


    @Override
    public UnifiedProtocol unPack(byte[] bs) {
        return null;
    }

    @Override
    public byte[] pack(PackData inMap) {
        return new byte[0];
    }
}
