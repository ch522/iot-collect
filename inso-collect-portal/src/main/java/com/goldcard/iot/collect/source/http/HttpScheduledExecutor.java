package com.goldcard.iot.collect.source.http;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author G002005
 * @Date 2020/4/23 16:24
 */
@Component
@Scope("singleton")
public class HttpScheduledExecutor {
    private static ScheduledExecutorService service;

    @PostConstruct
    private void init() {
        service = new ScheduledThreadPoolExecutor(1);
    }

    public ScheduledFuture<?> schedule(Runnable task, Long time, TimeUnit unit) {
        return service.schedule(task, time, unit);
    }

}
