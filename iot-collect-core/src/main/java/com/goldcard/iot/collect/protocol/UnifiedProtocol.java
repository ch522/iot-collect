package com.goldcard.iot.collect.protocol;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 统一协议
 *
 * @Author 2005
 * @Date 2020/1/29 下午2:51
 */
public class UnifiedProtocol {
    private String sessionId;
    private String protocolNo;
    private String cmdCode;
    private String deviceNo;
    private Date readTime;
    private Integer valveState;
    private String voltage;
    private String signal;
    private String temperature;
    private String pressure;
    private BigDecimal readNum;
    private BigDecimal workingTotal;
    private BigDecimal standardTotal;
    private BigDecimal workingInstant;
    private BigDecimal standardInstant;
    private Object data;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getProtocolNo() {
        return protocolNo;
    }

    public void setProtocolNo(String protocolNo) {
        this.protocolNo = protocolNo;
    }

    public String getCmdCode() {
        return cmdCode;
    }

    public void setCmdCode(String cmdCode) {
        this.cmdCode = cmdCode;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Integer getValveState() {
        return valveState;
    }

    public void setValveState(Integer valveState) {
        this.valveState = valveState;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public BigDecimal getReadNum() {
        return readNum;
    }

    public void setReadNum(BigDecimal readNum) {
        this.readNum = readNum;
    }

    public BigDecimal getWorkingTotal() {
        return workingTotal;
    }

    public void setWorkingTotal(BigDecimal workingTotal) {
        this.workingTotal = workingTotal;
    }

    public BigDecimal getStandardTotal() {
        return standardTotal;
    }

    public void setStandardTotal(BigDecimal standardTotal) {
        this.standardTotal = standardTotal;
    }

    public BigDecimal getWorkingInstant() {
        return workingInstant;
    }

    public void setWorkingInstant(BigDecimal workingInstant) {
        this.workingInstant = workingInstant;
    }

    public BigDecimal getStandardInstant() {
        return standardInstant;
    }

    public void setStandardInstant(BigDecimal standardInstant) {
        this.standardInstant = standardInstant;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
