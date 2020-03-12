package com.goldcard.iot.collect.protocol;

import java.util.Map;

/**
 * 组包数据实体
 * 
 * @author 2005
 */
public class PackData {

	private String protocolNo;
	private String cmdCode;
	private String deviceNo;
	private Map<String, Object> map;

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

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}
