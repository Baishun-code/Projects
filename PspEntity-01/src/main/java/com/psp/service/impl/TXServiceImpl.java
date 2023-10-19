package com.psp.service.impl;

import com.psp.entity.TxTransactionInfo;
import com.psp.mapper.TxTransactionInfoMapper;
import com.psp.service.TXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TXServiceImpl implements TXService {

    @Autowired
    private TxTransactionInfoMapper txTransactionInfoMapper;

    @Override
    public void writeToTxTable(TxTransactionInfo txTransactionInfo) {
        txTransactionInfoMapper.insert(txTransactionInfo);
    }

    @Override
    public void deleteFromTxTable(String serialNo) {
        txTransactionInfoMapper.deleleBySerialNo(serialNo);
    }

    @Override
    public List<TxTransactionInfo> queryAllTxRecords() {
        return txTransactionInfoMapper.queryAllRecords();
    }
}
