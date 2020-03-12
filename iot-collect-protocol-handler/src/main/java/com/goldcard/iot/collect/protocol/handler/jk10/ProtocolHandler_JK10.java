package com.goldcard.iot.collect.protocol.handler.jk10;

import com.goldcard.iot.collect.protocol.AbstractProtocolHandler;
import com.goldcard.iot.collect.protocol.UnifiedProtocol;
import com.goldcard.iot.collect.protocol.handler.jk10.cmd.AbstractCmdHandlerJk10;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Scope("prototype")
public class ProtocolHandler_JK10 extends AbstractProtocolHandler {
    @Override
    public String protocolNo() {
        return "JK10";
    }

    @Override
    public void handler(UnifiedProtocol item) {
        List<AbstractCmdHandlerJk10> cmds = super.getCmds(AbstractCmdHandlerJk10.class);
        Optional<AbstractCmdHandlerJk10> optional = cmds.stream().filter(p -> p.cmdCode().equals(item.getCmdCode())).findFirst();
        if (!optional.isPresent()) {
            throw new RuntimeException("协议:" + protocolNo() + ",命令:" + item.getCmdCode() + "没有实现处理方法");
        }
        AbstractCmdHandlerJk10 cmd = optional.get();
        cmd.handler(item);
    }
}
