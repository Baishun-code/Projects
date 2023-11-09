package com.psp.service;

import com.psp.entity.TxTransactionInfo;

public interface TxService {

    void insertTx(TxTransactionInfo txTransactionInfo);

    void removeTx(String serialNo);
}
