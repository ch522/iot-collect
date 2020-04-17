package com.goldcard.iot.collect.protocol.handler.model.pojo;

import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("Device")
public class Device {
    private Long id;

    private String no;

    private String devTypeCode;

    private String protocolNo;

    private String depCode;

    private String orgCode;

    private Date gmtCreate;

    private Date gmtModified;

    private Byte state;

    private Long creatorId;

    private Long modifierId;

    private Byte installState;

    private String noOld;

    private String commNo;

    private String externalId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getDevTypeCode() {
        return devTypeCode;
    }

    public void setDevTypeCode(String devTypeCode) {
        this.devTypeCode = devTypeCode == null ? null : devTypeCode.trim();
    }

    public String getProtocolNo() {
        return protocolNo;
    }

    public void setProtocolNo(String protocolNo) {
        this.protocolNo = protocolNo == null ? null : protocolNo.trim();
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode == null ? null : depCode.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    public Byte getInstallState() {
        return installState;
    }

    public void setInstallState(Byte installState) {
        this.installState = installState;
    }

    public String getNoOld() {
        return noOld;
    }

    public void setNoOld(String noOld) {
        this.noOld = noOld == null ? null : noOld.trim();
    }

    public String getCommNo() {
        return commNo;
    }

    public void setCommNo(String commNo) {
        this.commNo = commNo == null ? null : commNo.trim();
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId == null ? null : externalId.trim();
    }
}