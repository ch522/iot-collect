package com.goldcard.iot.collect.source.http;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class NbIotHandler extends HttpInboundHandlerAdapter {

    @Override
    protected void invoker(HttpHandlerContext ctx, Object msg) {

    }
}
