package com.psp.service;

import com.psp.entity.TfTransactionInfo;

public interface TransactionInfoService {

    void writeSerialRecord(TfTransactionInfo tfTransactionInfo);

    void changeTransactionState(String serialNo, String statusBefore, String status);
}
