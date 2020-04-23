package com.goldcard.iot.collect.source.http;

import java.io.Serializable;

public interface HttpHandlerContext extends Serializable {

    String sessionId();

    void fireUserEventTrigger();

    void fireInvoker(Object msg);

    void fireHandlerActive();

    HttpInboundHandler handler();

    void remove();


}
