package com.goldcard.iot.collect.subject;


import com.goldcard.iot.collect.cache.IotCacheManager;
import com.goldcard.iot.collect.observer.AbstractObserver;
import com.goldcard.iot.collect.observer.ISubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("subject")
@Scope("singleton")
public class SubjectImpl implements ISubject {
    @Autowired
    private IotCacheManager<String, AbstractObserver> iotCacheManager;

    public SubjectImpl() {

    }

    @Override
    public void register(AbstractObserver observer) {
        if (!hasObserver(observer.sessionId())) {
            iotCacheManager.getCache().put(observer.sessionId(), observer);
        }
    }

    @Override
    public void remove(AbstractObserver observer) {
        this.remove(observer.sessionId());
    }

    @Override
    public void remove(String sessionId) {
        if (hasObserver(sessionId)) {
            this.iotCacheManager.getCache().remove(sessionId);
        }
    }

    @Override
    public void send(String sessionId) {
        AbstractObserver observer = this.iotCacheManager.getCache().get(sessionId);
        if (null != observer) {
            observer.send();
        }
    }

    @Override
    public Boolean hasObserver(String sessionId) {
        return iotCacheManager.getCache().hasKey(sessionId);
    }
}
