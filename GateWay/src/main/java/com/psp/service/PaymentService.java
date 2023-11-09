package com.psp.service;

import com.psp.entity.ResponseV0;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-payment", path = "v1")
public interface PaymentService {

    @RequestMapping(value = "/transfer", method = RequestMethod.GET)
    ResponseV0 transferTo(@RequestParam("amt") double amt,
                          @RequestParam("currency") String currency,
                          @RequestParam("acctId") String acctId,
                          @RequestParam("targetAcctId") String targetAcctId,
                          @RequestParam("targetBank") String targetBank);

    @RequestMapping(value = "/withdraw", method = RequestMethod.GET)
    ResponseV0 withdrawFromAcct(@RequestParam("amt") double amt,
                                @RequestParam("currency") String currency,
                                @RequestParam("acctId") String acctId);

    @RequestMapping(value = "/topup", method = RequestMethod.GET)
    ResponseV0 DepositToAcct(@RequestParam("amt") double amt,
                             @RequestParam("currency") String currency,
                             @RequestParam("acctId") String acctId);

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    ResponseV0 test();

}
