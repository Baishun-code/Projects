package com.psp.service.impl;

import com.psp.entity.TfTransactionInfo;
import com.psp.service.AccountService;
import com.psp.service.BalanceService;
import com.psp.service.PaymentService;
import com.psp.service.TXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private BalanceService balanceService;
    @Autowired
    private TXService txService;

    /**
     *
     * @param info
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transfer(TfTransactionInfo info) {

        //deduct from current account
        String curAcctId = info.getAcctId();
        String currcd = info.getCurrency();
        double amt = info.getAmt();
        balanceService.deductFromAcct(curAcctId, currcd, amt);

        //increase another account
        String targetAcctBank = info.getTargetAcctBank();
        if("bank-01".equals(targetAcctBank)){
            //inner bank transfer
            String targetAcctId = info.getTargetAcctId();
            String currency = info.getCurrency();
            balanceService.topUpToAcct(targetAcctId, currency, amt);

        }else if("bank-02".equals(targetAcctBank)){
            //transfer to another bank or third party
            //write into tx table
            txService.writeToTxTable(info);
        }else {

        }
    }
}
