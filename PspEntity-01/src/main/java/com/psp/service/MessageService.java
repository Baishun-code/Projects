package com.psp.service;

import com.psp.entity.TfTransactionInfo;
import com.psp.entity.TxTransactionInfo;

import java.util.List;

public interface MessageService {

    List<TxTransactionInfo> getTxData();

    int cancelTxData(List<String> data);

}
