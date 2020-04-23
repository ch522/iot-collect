package com.goldcard.iot.collect.source.http;

import java.util.concurrent.ExecutorService;

public class AbstractHttpHandlerContext implements HttpHandlerContext {

    private ExecutorService executor;
    private HttpInboundHandler handler;
    private String sessionId;
    private int state = 1;
    private HttpIdleHandler idleHandler;

    public AbstractHttpHandlerContext(ExecutorService executor, HttpInboundHandler handler, HttpIdleHandler idleHandler, String sessionId) {
        this.executor = executor;
        this.handler = handler;
        this.sessionId = sessionId;
        this.idleHandler = idleHandler;
        initialize();
    }

    private void initialize() {
        fireHandlerActive();
        state = 2;
    }


    @Override
    public String sessionId() {
        return sessionId;
    }

    @Override
    public void fireUserEventTrigger() {
        executor.execute(() -> {
            handler.userEventTrigger(this);
        });
    }

    @Override
    public void fireHandlerActive() {
        if (state == 1) {
            handler.httpChannelActive(this);
        } else {
            executor.execute(() -> {
                handler.httpChannelActive(this);
            });
        }
    }

    @Override
    public void fireInvoker(Object msg) {
        if (state == 1) {
            handler.httpHandler(this, msg);
        } else {
            executor.execute(() -> {
                handler.httpHandler(this, msg);
            });
        }

    }

    @Override
    public void remove() {
        this.state = 3;
        executor.execute(() -> {
            handler.httpChannelRemove(this);
        });
    }

    @Override
    public HttpInboundHandler handler() {
        return handler;
    }
}
