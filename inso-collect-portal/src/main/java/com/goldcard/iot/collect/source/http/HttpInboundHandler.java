package com.goldcard.iot.collect.source.http;

public interface HttpInboundHandler {

    void httpHandler(HttpHandlerContext context, Object msg);

    void userEventTrigger(HttpHandlerContext context);

    void httpChannelActive(HttpHandlerContext ctx);

    void httpChannelRemove(HttpHandlerContext ctx);
}
