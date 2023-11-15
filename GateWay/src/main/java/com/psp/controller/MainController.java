package com.psp.controller;

import com.psp.entity.ResponseV0;
import com.psp.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/v1")
public class MainController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/deposit")
    public ResponseV0 deposit(@RequestParam("amt") double amt,
                                 @RequestParam("currency") String currency,
                                 @RequestParam("acctId") String acctId){
        ResponseV0 responseV0 = null;
        try {
            responseV0 = paymentService.DepositToAcct(amt, currency, acctId);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseV0.fail("Fail to deposit");
        }
        return responseV0;
    }

    @RequestMapping("/withdraw")
    public ResponseV0 withdraw(@RequestParam("amt") double amt,
                               @RequestParam("currency") String currency,
                               @RequestParam("acctId") String acctId){
        ResponseV0 responseV0 = null;
        try {
            responseV0 = paymentService.withdrawFromAcct(amt, currency, acctId);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseV0.fail("Fail to withdraw");
        }
        return responseV0;
    }

    @RequestMapping("/transfer")
    public ResponseV0 transferTo(@RequestParam("amt") double amt,
                                 @RequestParam("currency") String currency,
                                 @RequestParam("acctId") String acctId,
                                 @RequestParam("targetAcctId") String targetAcctId,
                                 @RequestParam("targetBank") String targetBank){
        ResponseV0 responseV0 = null;
        try {
            responseV0 = paymentService.transferTo(amt, currency, acctId, targetAcctId, targetBank);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseV0.fail("Fail to transfer");
        }
        return responseV0;
    }



}
