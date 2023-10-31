package com.psp.service.impl;

import com.psp.common.TxStatus;
import com.psp.entity.TfTransactionInfo;
import com.psp.service.*;
import com.psp.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private BalanceService balanceService;
    @Autowired
    private TXService txService;
    @Value("${bank-entity.current-bank}")
    private String curBank;
    @Autowired
    private TransactionInfoService transactionInfoService;
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

        String serialNum = generateSerialNum(info);
        log.info("Generate serial number {}", serialNum);

        info.setSerialNo(serialNum);
        //deduct from acct
        log.info("Deduct from balance");
        balanceService.deductFromAcct(curAcctId, currcd, amt);

        //increase another account
        String targetAcctBank = info.getTargetAcctBank();
        if(curBank.equals(targetAcctBank)){
            //inner bank transfer, doesn't need to write to tx table
            // since there is no interbank transfer
            log.info("Inner bank transfering");
            String targetAcctId = info.getTargetAcctId();
            String currency = info.getCurrency();
            balanceService.topUpToAcct(targetAcctId, currency, amt);
            //once successfully finish inner bank transfer
            //the transaction finished
            //set the state of current transaction to be FINISHED
            info.setTxStatus(TxStatus.FINISHED.code);
            info.setEndDt(new Date());
        }else {
            //transfer to another bank or third party
            //write into tx table
            log.info("Write tx record");
            info.setTxStatus(TxStatus.TRANSFERING.code);
            txService.writeToTxTable(info);
        }

        //write serial record
        log.info("write serial record");
        transactionInfoService.writeSerialRecord(info);
    }

    private String generateSerialNum(TfTransactionInfo info){
        String stringDate = Util.getStringDate().substring(3);
        String acctId = info.getAcctId().substring(6);
        return "S".concat(stringDate).concat(acctId);
    }

}


