package com.psp.service;

import com.psp.entity.TfTransactionInfo;

import java.sql.Date;


public interface TransactionInfoService {

    void writeSerialRecord(TfTransactionInfo tfTransactionInfo);

    void changeTransactionState(String serialNo, String statusBefore, String status, Date finishTime);

    TfTransactionInfo getTransactionBySerialNo(String serialNo);
}
