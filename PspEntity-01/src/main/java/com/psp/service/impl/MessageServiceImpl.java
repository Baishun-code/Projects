package com.psp.service.impl;

import com.psp.entity.TfTransactionInfo;
import com.psp.mapper.TfTransactionInfomMapper;
import com.psp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.ldap.PagedResultsControl;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private TfTransactionInfomMapper tfTransactionInfomMapper;

    @Override
    public List<TfTransactionInfo> getTxData() {
        return tfTransactionInfomMapper.queryAllRecords();
    }

    @Override
    public int cancelTxData(List<String> ids) {
        return 0;
    }
}
