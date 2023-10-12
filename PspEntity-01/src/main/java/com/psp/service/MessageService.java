package com.psp.service;

import com.psp.entity.TfTransactionInfo;

import java.util.List;

public interface MessageService {

    List<TfTransactionInfo> getTxData();

    int cancelTxData(List<String> data);

}
