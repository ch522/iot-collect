package com.goldcard.iot.collect.observer;

/**
 * 抽象观察者
 *
 * @author 2005
 */
public abstract class AbstractObserver {

    /**
     * 通道号
     */
    public abstract String sessionId();

    public abstract void send();
}
