package com.psp.service.impl;

import com.psp.entity.TfTransactionInfo;
import com.psp.mapper.TfTransactionInfomMapper;
import com.psp.service.TransactionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionInfoServiceImpl implements TransactionInfoService {

    @Autowired
    private TfTransactionInfomMapper tfTransactionInfomMapper;

    @Override
    public void writeSerialRecord(TfTransactionInfo tfTransactionInfo) {
        tfTransactionInfomMapper.insert(tfTransactionInfo);
    }
}
