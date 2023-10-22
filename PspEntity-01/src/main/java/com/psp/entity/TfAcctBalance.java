package com.psp.entity;

import java.io.Serializable;

/**
 * tf_acct_balance
 * @author 
 */
public class TfAcctBalance implements Serializable {
    private String acctId;

    private Double balance;

    private String currency;

    private static final long serialVersionUID = 1L;

    public String getAcctId() {
        return acctId;
    }

    public void setAcctId(String acctId) {
        this.acctId = acctId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}