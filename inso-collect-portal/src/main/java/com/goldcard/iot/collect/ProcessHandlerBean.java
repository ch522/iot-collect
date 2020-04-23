package com.goldcard.iot.collect;

/**
 * @Author G002005
 * @Date 2020/4/17 15:45
 */
public class ProcessHandlerBean {
    private String sessionId;
    private String protocolCode;
    private byte[] data;
    private String hexData;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getHexData() {
        return hexData;
    }

    public void setHexData(String hexData) {
        this.hexData = hexData;
    }
}
