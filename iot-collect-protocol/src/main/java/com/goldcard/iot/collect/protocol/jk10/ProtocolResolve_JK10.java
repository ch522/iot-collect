package com.goldcard.iot.collect.protocol.jk10;

import com.goldcard.iot.collect.protocol.AbstractProtocolResolve;
import com.goldcard.iot.collect.protocol.PackData;
import com.goldcard.iot.collect.protocol.UnifiedProtocol;
import com.goldcard.iot.collect.protocol.jk10.cmd.AbstractCmdResolveJk10;
import com.goldcard.iot.collect.util.CommonUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    private String getCmdCode(byte[] inArray) {
        byte[] temp = new byte[2];
        System.arraycopy(inArray, 3, temp, 0, temp.length);
        return CommonUtil.byte2Hex(temp);
    }

    @Override
    public UnifiedProtocol unPack(byte[] bs) {
        String cmdCode = getCmdCode(bs);
        List<AbstractCmdResolveJk10> cmds = super.getCmds(AbstractCmdResolveJk10.class);
        Optional<AbstractCmdResolveJk10> optional = cmds.stream().filter(p -> p.cmdCode().equals(cmdCode)).findFirst();
        if (!optional.isPresent()) {
            throw new RuntimeException("协议:" + protocolNo() + ",命令:" + cmdCode + "没有实现解析");
        }
        AbstractCmdResolveJk10 cmd = optional.get();
        UnifiedProtocol item = cmd.unPack(bs);
        return item;
    }

    @Override
    public byte[] pack(PackData inMap) {
        return new byte[0];
    }
}
