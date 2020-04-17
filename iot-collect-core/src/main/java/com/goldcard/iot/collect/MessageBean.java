package com.goldcard.iot.collect;

import com.goldcard.protocol.InwardCommand;

/**
 * @Author G002005
 * @Date 2020/4/17 15:36
 */
public class MessageBean {
    private String sessionId;
    private InwardCommand item;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public InwardCommand getItem() {
        return item;
    }

    public void setItem(InwardCommand item) {
        this.item = item;
    }
}
