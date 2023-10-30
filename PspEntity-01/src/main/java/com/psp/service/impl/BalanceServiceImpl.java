package com.psp.service.impl;

import com.psp.mapper.TfAcctBalanceMapper;
import com.psp.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private TfAcctBalanceMapper tfAcctBalanceMapper;

    @Override
    public void deductFromAcct(String acctId, String currcd, double amt) {
        synchronized (this){
            double currentBalance = tfAcctBalanceMapper.queryBalanceByIdCurr(acctId, currcd);
            if(currentBalance < amt){
                return;
            }
            tfAcctBalanceMapper.deductAcctBalance(acctId, currcd, -amt);
        }

    }

    @Override
    public void topUpToAcct(String acctId, String currcd, double amt) {
        tfAcctBalanceMapper.deductAcctBalance(acctId, currcd, amt);
    }
}
