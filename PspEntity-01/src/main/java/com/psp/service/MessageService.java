package com.psp.service;

import com.psp.entity.TfTransactionInfo;
import com.psp.entity.TxTransactionInfo;

import java.util.List;
import java.util.Map;

public interface MessageService {

    Map<String, TxTransactionInfo> getTxData();

    int cancelTxData(List<String> data);

}
