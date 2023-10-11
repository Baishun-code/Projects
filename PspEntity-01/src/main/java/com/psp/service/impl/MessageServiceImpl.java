package com.psp.service.impl;

import com.psp.mapper.TfTransactionInfomMapper;
import com.psp.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public List<TfTransactionInfomMapper> getTxData() {
        return null;
    }

    @Override
    public int cancelTxData(List<String> ids) {
        return 0;
    }
}
