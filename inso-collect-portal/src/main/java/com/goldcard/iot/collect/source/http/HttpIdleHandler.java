package com.goldcard.iot.collect.source.http;

import java.io.Serializable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Author G002005
 * @Date 2020/4/23 16:35
 */
public class HttpIdleHandler implements Serializable {
    private static final long MIN_TIMEOUT_NANOS;
    private long idleTimeNanos;
    private Long lastOperatorTime;
    private ScheduledFuture<?> future;
    private HttpHandlerContext ctx;

    static {
        MIN_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(1L);
    }

    private long ticksInNanos() {
        return System.nanoTime();
    }

    public HttpIdleHandler(Long timeOut, TimeUnit unit) {
        if (unit == null) {
            throw new NullPointerException("unit");
        } else {
            if (timeOut <= 0L) {
                this.idleTimeNanos = 0L;
            } else {
                this.idleTimeNanos = Math.max(unit.toNanos(timeOut), MIN_TIMEOUT_NANOS);
            }
        }
    }

    private void initialize() {

    }


}
