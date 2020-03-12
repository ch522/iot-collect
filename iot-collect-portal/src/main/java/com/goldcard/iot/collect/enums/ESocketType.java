package com.goldcard.iot.collect.enums;

public enum ESocketType {
    TCP_SHORT("tcp_short"), TCP_LONG("tcp_long"), UDP("udp");
    private String code;

    private ESocketType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
