package com.psp.service;

import com.psp.entity.TxTransactionInfo;

public interface InnerBankTransferService {

    void transfer(TxTransactionInfo txTransactionInfo);
}
