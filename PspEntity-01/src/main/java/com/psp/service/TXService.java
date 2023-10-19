package com.psp.service;

import com.psp.entity.TxTransactionInfo;

import java.util.List;

public interface TXService {

    void writeToTxTable(TxTransactionInfo txTransactionInfo);

    void deleteFromTxTable(String serialNo);

    List<TxTransactionInfo> queryAllTxRecords();

}
