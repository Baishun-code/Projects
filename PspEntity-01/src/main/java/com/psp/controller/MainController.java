package com.psp.controller;

import com.psp.common.TxStatus;
import com.psp.entity.ResponseV0;
import com.psp.entity.TfTransactionInfo;
import com.psp.entity.TxTransactionInfo;
import com.psp.service.PaymentService;
import com.psp.service.impl.MessageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
@Slf4j
public class MainController {

    @Autowired
    private MessageServiceImpl messageService;
    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/getTxdata")
    public ResponseV0 getAllTxData(){
        Map<String, TxTransactionInfo> txData = messageService.getTxData();
        return ResponseV0.success(txData);
    }

    @RequestMapping("/transfer")
    public ResponseV0 transfer(@Param("amt") double amt,
                               @Param("currency") String currency,
                               @Param("acctId") String acctId,
                               @Param("targetAcctId") String targetAcctId,
                               @Param("targetBank") String targetBank){
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
            log.info(e.getMessage());
            return ResponseV0.fail("Fail to transfer due to " + e.getMessage());
        }
    }


}
