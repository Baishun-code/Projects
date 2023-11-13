package com.psp.entity;

import java.io.Serializable;

/**
 * td_bank_message_info
 * @author 
 */
public class TdBankMessageInfo implements Serializable {
    private String bankId;

    private String bankName;

    private String encodeType;

    private String reqType;

    private String bankIp;

    private String bankPort;

    private String bankUrl;

    private static final long serialVersionUID = 1L;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getBankIp() {
        return bankIp;
    }

    public void setBankIp(String bankIp) {
        this.bankIp = bankIp;
    }

    public String getBankPort() {
        return bankPort;
    }

    public void setBankPort(String bankPort) {
        this.bankPort = bankPort;
    }

    public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl;
    }
}