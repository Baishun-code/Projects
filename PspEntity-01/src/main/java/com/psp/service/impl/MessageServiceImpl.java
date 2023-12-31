package com.psp.service.impl;

import com.psp.entity.TfTransactionInfo;
import com.psp.entity.TxTransactionInfo;
import com.psp.mapper.TfTransactionInfomMapper;
import com.psp.service.MessageService;
import com.psp.service.TXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * not used, its funcationality is implemented by txService
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private TfTransactionInfomMapper tfTransactionInfomMapper;

    @Autowired
    private TXService txService;

    @Override
    public Map<String, TxTransactionInfo> getTxData() {
        return txService.queryAllTxRecords();
    }

    @Override
    public int cancelTxData(List<String> ids) {
        return 0;
    }
}
