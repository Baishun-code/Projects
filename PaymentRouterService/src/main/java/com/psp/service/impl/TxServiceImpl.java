package com.psp.service.impl;

import com.psp.entity.TxTransactionInfo;
import com.psp.mapper.TxTransactionInfoMapper;
import com.psp.service.TxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TxServiceImpl implements TxService {

    @Autowired
    private TxTransactionInfoMapper txTransactionInfoMapper;

    @Override
    public void insertTx(TxTransactionInfo txTransactionInfo) {
        txTransactionInfoMapper.insert(txTransactionInfo);
    }

    @Override
    public void removeTx(String serialNo) {
        txTransactionInfoMapper.deleteBySerialNo(serialNo);
    }
}
