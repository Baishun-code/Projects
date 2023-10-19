package com.psp.entity;


import java.io.Serializable;
import java.util.Date;

/**
 * tx_transaction_info
 * @author 
 */
public class TxTransactionInfo implements Serializable {
    private String serialNo;

    private Date createDt;

    private Date endDt;

    private Double amt;

    private String currency;

    private String acctId;

    private String targetAcctId;

    private String targetAcctBank;

    private String txStatus;

    private Date insertDt;

    private static final long serialVersionUID = 1L;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public String getTargetAcctId() {
        return targetAcctId;
    }

    public void setTargetAcctId(String targetAcctId) {
        this.targetAcctId = targetAcctId;
    }

    public String getTargetAcctBank() {
        return targetAcctBank;
    }

    public void setTargetAcctBank(String targetAcctBank) {
        this.targetAcctBank = targetAcctBank;
    }

    public String getTxStatus() {
        return txStatus;
    }

    public void setTxStatus(String txStatus) {
        this.txStatus = txStatus;
    }

    public Date getInsertDt() {
        return insertDt;
    }

    public void setInsertDt(Date insertDt) {
        this.insertDt = insertDt;
    }
}