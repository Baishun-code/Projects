package com.psp.common;

public enum TxStatus {

    TRANSFERING("001", "Tansfering"),
    FINISHED("002", "Finished"),
    FAILED("003", "Failed"),
    REFUNDING("004", "Refunding"),
    REFUNDED("005", "Refunded");


    public String code;
    public String codeDesc;

    TxStatus(String code, String codeDesc){
        this.code = code;
        this.codeDesc = codeDesc;
    }
}
