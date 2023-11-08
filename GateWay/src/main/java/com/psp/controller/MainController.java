package com.psp.controller;

import com.psp.entity.ResponseV0;
import com.psp.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MainController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/test")
    public ResponseV0 test(){
        ResponseV0 responseV0 = paymentService.test();
        return responseV0;
    }

}
