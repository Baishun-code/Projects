package com.psp.controller;

import com.psp.entity.ResponseV0;
import com.psp.entity.TxTransactionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MainController {

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @RequestMapping("/innerbank")
    public ResponseV0 innerBankTransfer(@RequestParam("amt") double amt,
                                        @RequestParam("currency") String currency,
                                        @RequestParam("acctId") String acctId,
                                        @RequestParam("targetAcctId") String targetAcctId,
                                        @RequestParam("targetBank") String targetBank){
        try {
            TxTransactionInfo txTransactionInfo = new TxTransactionInfo();
            txTransactionInfo.setCurrency(currency);
            txTransactionInfo.setTargetAcctBank(targetBank);
            txTransactionInfo.setTargetAcctId(targetAcctId);
            txTransactionInfo.setAcctId(acctId);
            txTransactionInfo.setAmt(amt);

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseV0.fail("Fail to transfer");
        }
        return ResponseV0.success("Transfer successfully");
    }


}
