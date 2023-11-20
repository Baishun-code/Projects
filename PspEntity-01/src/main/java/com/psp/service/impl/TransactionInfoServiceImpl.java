package com.psp.service.impl;

import com.psp.entity.TfTransactionInfo;
import com.psp.mapper.TfTransactionInfomMapper;
import com.psp.service.TransactionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;


@Service
public class TransactionInfoServiceImpl implements TransactionInfoService {

    @Autowired
    private TfTransactionInfomMapper tfTransactionInfomMapper;

    @Override
    public void writeSerialRecord(TfTransactionInfo tfTransactionInfo) {
        tfTransactionInfomMapper.insert(tfTransactionInfo);
    }

    /**
     *
     * @param serialNo
     * @param statusBefore
     * @param status
     *
     * Use optimistic lock which is implemented by database record lock
     * to guarantee idempotent, if the message is already processed,
     * the statusBefore will not match, so the record can only be updated
     * for one time
     */
    @Override
    public void changeTransactionState(String serialNo, String statusBefore, String status, Date finishTime) {
        tfTransactionInfomMapper.updateTransactionStatus(serialNo, statusBefore, status, finishTime);
    }

    @Override
    public TfTransactionInfo getTransactionBySerialNo(String serialNo) {
        return tfTransactionInfomMapper.queryTransactionBySerialNo(serialNo);
    }
}
