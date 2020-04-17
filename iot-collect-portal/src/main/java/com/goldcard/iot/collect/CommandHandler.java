package com.goldcard.iot.collect;

import com.goldcard.iot.collect.protocol.*;
import com.goldcard.protocol.InwardCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CommandHandler {

    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    public void process(ProcessHandlerBean bean) {
        AbstractProtocolResolve protocol = AbstractProtocolResolveFactory.create(bean.getProtocolCode());
        AbstractProtocolHandler handler = AbstractProtocolHandlerFactory.create(bean.getProtocolCode());
        if (null == protocol) {
            throw new RuntimeException("协议" + bean.getProtocolCode() + "未实现");
        }
        if (Objects.isNull(handler)) {
            throw new RuntimeException("协议" + bean.getProtocolCode() + "未实现处理");
        }
        InwardCommand item = protocol.unPack(bean.getData());

        MessageBean messageBean = new MessageBean() {{
            setItem(item);
            setSessionId(bean.getSessionId());
        }};
        handler.handler(messageBean);
    }
}
