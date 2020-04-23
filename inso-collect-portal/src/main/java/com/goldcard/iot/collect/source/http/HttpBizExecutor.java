package com.goldcard.iot.collect.source.http;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HTTP业务处理线程池
 *
 * @Author G002005
 * @Date 2020/4/23 10:12
 */
@Component
@Scope("singleton")
public class HttpBizExecutor {
    private static final int THREAD_POOL_THREADS;
    private static final int THREAD_POOL_QUEUES;
    private static ExecutorService executorPool;

    static {
        THREAD_POOL_THREADS = Runtime.getRuntime().availableProcessors() * 32;
        THREAD_POOL_QUEUES = THREAD_POOL_THREADS / 16;
    }

    @PostConstruct
    private void init() {
        executorPool = new ThreadPoolExecutor(THREAD_POOL_THREADS, THREAD_POOL_THREADS * 2,
                60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(THREAD_POOL_QUEUES), new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "httpHandlerThread_" + this.threadIndex.incrementAndGet());
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());

    }

//    public ExecutorService getExecutor() {
//        return executorPool;
//    }

    public void execute(Runnable task) {
        executorPool.execute(task);
    }

}
