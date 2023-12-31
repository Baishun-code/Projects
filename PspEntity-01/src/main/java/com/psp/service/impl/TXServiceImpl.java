package com.psp.service.impl;

import com.psp.entity.TfTransactionInfo;
import com.psp.entity.TxTransactionInfo;
import com.psp.mapper.TxTransactionInfoMapper;
import com.psp.service.TXService;
import com.psp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TXServiceImpl implements TXService {

    @Autowired
    private TxTransactionInfoMapper txTransactionInfoMapper;

    @Override
    public void writeToTxTable(TxTransactionInfo txTransactionInfo) {
        txTransactionInfoMapper.insert(txTransactionInfo);
    }

    public void writeToTxTable(TfTransactionInfo tfTransactionInfo) {

        TxTransactionInfo txTransactionInfo = new TxTransactionInfo();
        txTransactionInfo.setAcctId(tfTransactionInfo.getAcctId());
        txTransactionInfo.setCreateDt(tfTransactionInfo.getCreateDt());
        txTransactionInfo.setAmt(tfTransactionInfo.getAmt());
        txTransactionInfo.setSerialNo(tfTransactionInfo.getSerialNo());
        txTransactionInfo.setEndDt(tfTransactionInfo.getEndDt());
        txTransactionInfo.setTargetAcctBank(tfTransactionInfo.getTargetAcctBank());
        txTransactionInfo.setTargetAcctId(tfTransactionInfo.getTargetAcctId());
        txTransactionInfo.setInsertDt(Util.getDataDate());
        txTransactionInfo.setCurrency(tfTransactionInfo.getCurrency());
        txTransactionInfo.setTxStatus(tfTransactionInfo.getTxStatus());

        txTransactionInfoMapper.insert(txTransactionInfo);
    }

    @Override
    public void deleteFromTxTable(String serialNo) {
        txTransactionInfoMapper.deleleBySerialNo(serialNo);
    }

    @Override
    public Map<String, TxTransactionInfo> queryAllTxRecords() {
        HashMap<String, TxTransactionInfo> map = new HashMap<>();

        for (TxTransactionInfo queryAllRecord : txTransactionInfoMapper.queryAllRecords()) {
            map.put(queryAllRecord.getSerialNo(), queryAllRecord);
        }
        return map;
    }
}
