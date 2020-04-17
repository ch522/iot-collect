package com.goldcard.iot.collect;

import com.goldcard.protocol.OutwardCommand;

import java.util.Date;

/**
 * @Author G002005
 * @Date 2020/4/17 15:27
 */
public class CmdSendPool {
    private Boolean hasEnd = Boolean.FALSE;
    private OutwardCommand outwardCommand;
    private Date sendDate;
    private Long commandId;

    public Boolean getHasEnd() {
        return hasEnd;
    }

    public void setHasEnd(Boolean hasEnd) {
        this.hasEnd = hasEnd;
    }

    public OutwardCommand getOutwardCommand() {
        return outwardCommand;
    }

    public void setOutwardCommand(OutwardCommand outwardCommand) {
        this.outwardCommand = outwardCommand;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Long getCommandId() {
        return commandId;
    }

    public void setCommandId(Long commandId) {
        this.commandId = commandId;
    }
}
