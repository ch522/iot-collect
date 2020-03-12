package com.goldcard.iot.collect.configure;
/**
 * SOCKET通讯服务配置
@Author 2005
@Date 2020/1/29 下午4:57
*/
public class SocketServerConfigure {
    //类型
    private String type;
    //端口
    private Integer port;
    //超时时间
    private Integer timeOut;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }
}
