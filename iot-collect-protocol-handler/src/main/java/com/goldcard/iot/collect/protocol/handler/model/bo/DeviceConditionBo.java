package com.goldcard.iot.collect.protocol.handler.model.bo;

import org.apache.ibatis.type.Alias;

import java.util.List;

@Alias("DeviceConditionBo")
public class DeviceConditionBo extends BaseCondition {
    private String no;

    private List<Integer> states;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public List<Integer> getStates() {
        return states;
    }

    public void setStates(List<Integer> states) {
        this.states = states;
    }
}