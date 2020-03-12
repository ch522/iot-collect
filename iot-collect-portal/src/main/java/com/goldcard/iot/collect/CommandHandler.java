package com.goldcard.iot.collect;

import com.goldcard.iot.collect.protocol.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CommandHandler {

    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    public void process(InwardCmd inwardCmd) {
        AbstractProtocolResolve protocol = AbstractProtocolResolveFactory.create(inwardCmd.getProtocolCode());
        AbstractProtocolHandler handler = AbstractProtocolHandlerFactory.create(inwardCmd.getProtocolCode());
        if (null == protocol) {
            throw new RuntimeException("协议" + inwardCmd.getProtocolCode() + "未实现");
        }
        if (Objects.isNull(handler)) {
            throw new RuntimeException("协议" + inwardCmd.getProtocolCode() + "未实现处理");
        }
        UnifiedProtocol item = protocol.unPack(inwardCmd.getData());
        item.setSessionId(inwardCmd.getSessionId());
        handler.handler(item);
    }
}
