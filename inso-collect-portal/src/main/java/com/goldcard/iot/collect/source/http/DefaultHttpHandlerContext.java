package com.goldcard.iot.collect.source.http;

import java.util.concurrent.ExecutorService;

public class DefaultHttpHandlerContext extends AbstractHttpHandlerContext {

    public DefaultHttpHandlerContext(ExecutorService executor, HttpInboundHandler handler, HttpIdleHandler idleHandler, String sessionId) {
        super(executor, handler, idleHandler, sessionId);
    }

}
