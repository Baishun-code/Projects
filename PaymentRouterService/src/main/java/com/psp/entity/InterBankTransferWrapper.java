package com.psp.entity;

public class InterBankTransferWrapper {

    private String ipPort;
    private String targetAcct;
    private String currency;
    private double amt;

    public InterBankTransferWrapper(String ipPort, String targetAcct, String currency, double amt) {
        this.ipPort = ipPort;
        this.targetAcct = targetAcct;
        this.currency = currency;
        this.amt = amt;
    }

    public InterBankTransferWrapper() {
    }

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }

    public String getTargetAcct() {
        return targetAcct;
    }

    public void setTargetAcct(String targetAcct) {
        this.targetAcct = targetAcct;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }
}
