package com.psp.service.impl;

import com.psp.entity.TfFinishedTransaction;
import com.psp.mapper.TfFinishedTransactionMapper;
import com.psp.service.FinishedTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinishedTransactionServiceImpl implements FinishedTransactionService {

    @Autowired
    private TfFinishedTransactionMapper tfFinishedTransactionMapper;

    @Override
    public void createNewFinishedTransac(TfFinishedTransaction record) {
        tfFinishedTransactionMapper.insert(record);
    }

    @Override
    public List<TfFinishedTransaction> fetchTransaction() {
        return tfFinishedTransactionMapper.queryAllTransactions();
    }

    @Override
    public void cancelTransaction(String serialNo) {
        tfFinishedTransactionMapper.deleteTransaction(serialNo);
    }
}
