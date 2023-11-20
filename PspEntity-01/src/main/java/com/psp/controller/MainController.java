package com.psp.controller;

import com.psp.common.TxStatus;
import com.psp.entity.ResponseV0;
import com.psp.entity.TfTransactionInfo;
import com.psp.entity.TxTransactionInfo;
import com.psp.mapper.TxTransactionInfoMapper;
import com.psp.service.PaymentService;
import com.psp.service.TransactionInfoService;
import com.psp.service.impl.MessageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
@Slf4j
public class MainController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private TransactionInfoService transactionInfoService;

    @RequestMapping("/transfer")
    public ResponseV0 transfer(@RequestParam("amt") double amt,
                               @RequestParam("currency") String currency,
                               @RequestParam("acctId") String acctId,
                               @RequestParam("targetAcctId") String targetAcctId,
                               @RequestParam("targetBank") String targetBank){
        try {
            log.info("Request for transfering from {} coming in ...", acctId);
            TfTransactionInfo info = new TfTransactionInfo();
            info.setAmt(amt);
            info.setAcctId(acctId);
            info.setCreateDt(new Date());
            info.setCurrency(currency);
            log.info("Target bank {}", targetBank);
            info.setTargetAcctBank(targetBank);
            info.setTargetAcctId(targetAcctId);
            info.setTxStatus(TxStatus.TRANSFERING.code);
            paymentService.transfer(info);
            return ResponseV0.success("Transfer success");
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseV0.fail("Fail to transfer due to " + e.getMessage());
        }
    }

    @RequestMapping("/topup")
    public ResponseV0 topUp(@RequestParam("amt") double amt,
                            @RequestParam("currency") String currency,
                            @RequestParam("acctId") String acctId){
        try {
            TfTransactionInfo info = new TfTransactionInfo();
            info.setAcctId(acctId);
            info.setCurrency(currency);
            info.setAmt(amt);
            paymentService.deposit(info);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseV0.fail(e.getMessage());
        }
        return ResponseV0.success("Successfully deposited to account");
    }

    @RequestMapping("/withdraw")
    public ResponseV0 withdraw(@RequestParam("amt") double amt,
                               @RequestParam("currency") String currency,
                               @RequestParam("acctId") String acctId){
        try {
            TfTransactionInfo info = new TfTransactionInfo();
            info.setAcctId(acctId);
            info.setCurrency(currency);
            info.setAmt(amt);
            paymentService.withdraw(info);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseV0.fail(e.getMessage());
        }
        return ResponseV0.success("Successfully to account");
    }

    @RequestMapping("/transaction")
    public ResponseV0 queryTransaction(@RequestParam("serialNo") String serialNo){
        try {
            TfTransactionInfo transactionBySerialNo =
                    transactionInfoService.getTransactionBySerialNo(serialNo);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseV0.fail("Unable to fetch");
        }
        return ResponseV0.success("success", transactionInfoService);
    }
}
