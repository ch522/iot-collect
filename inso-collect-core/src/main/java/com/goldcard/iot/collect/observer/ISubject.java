package com.goldcard.iot.collect.observer;


public interface ISubject {

    void register(AbstractObserver observer);

    void remove(AbstractObserver observer);

    void remove(String sessionId);

    void send(String sessionId);

    Boolean hasObserver(String sessionId);

}
