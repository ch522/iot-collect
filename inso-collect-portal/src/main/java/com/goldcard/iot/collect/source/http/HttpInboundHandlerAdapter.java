package com.goldcard.iot.collect.source.http;

public abstract class HttpInboundHandlerAdapter implements HttpInboundHandler {
    protected abstract void invoker(HttpHandlerContext ctx, Object msg);


    @Override
    public void httpHandler(HttpHandlerContext context, Object msg) {
        this.invoker(context, msg);
    }

    @Override
    public void userEventTrigger(HttpHandlerContext context) {

    }

    @Override
    public void httpChannelActive(HttpHandlerContext ctx) {

    }

    @Override
    public void httpChannelRemove(HttpHandlerContext ctx) {

    }
}
