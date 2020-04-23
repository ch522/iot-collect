package com.goldcard.iot.collect.rule;

import java.io.Serializable;

public class ProtocolRule implements Serializable {
    private byte[] data;
    private Integer size;
    private String protocolCode;
    private String hexStr;
    private Boolean hasRegister = Boolean.FALSE;
    private String regResponse;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getProtocolCode() {
        return protocolCode;
    }

    public void setProtocolCode(String protocolCode) {
        this.protocolCode = protocolCode;
    }

    public String getHexStr() {
        return hexStr;
    }

    public void setHexStr(String hexStr) {
        this.hexStr = hexStr;
    }

    public Boolean getHasRegister() {
        return hasRegister;
    }

    public void setHasRegister(Boolean hasRegister) {
        this.hasRegister = hasRegister;
    }

    public String getRegResponse() {
        return regResponse;
    }

    public void setRegResponse(String regResponse) {
        this.regResponse = regResponse;
    }

    public static class Builder {
        private byte[] data;
        private String hexStr;
        private Integer size;

        public Builder data(byte[] data) {
            this.data = data;
            return this;
        }

        public Builder hexStr(String hexStr) {
            this.hexStr = hexStr;
            return this;
        }

        public Builder size(Integer size) {
            this.size = size;
            return this;
        }

        public ProtocolRule build() {
            return new ProtocolRule(this);
        }
    }

    private ProtocolRule(Builder builder) {
        this.data = builder.data;
        this.hexStr = builder.hexStr;
        this.size = builder.size;
    }
}
