package com.psp.service;

import com.psp.mapper.TfTransactionInfomMapper;

import java.util.List;

public interface MessageService {

    List<TfTransactionInfomMapper> getTxData();

    int cancelTxData(List<String> data);

}
