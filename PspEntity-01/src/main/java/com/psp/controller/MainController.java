package com.psp.controller;

import com.psp.entity.ResponseV0;
import com.psp.entity.TfTransactionInfo;
import com.psp.service.impl.MessageServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    private MessageServiceImpl messageService;

    @RequestMapping("/test")
    public String Test(){
        return "test";
    }

    @RequestMapping("/getTxdata")
    public ResponseV0 getAllTxData(){
        List<TfTransactionInfo> txData = messageService.getTxData();
        return ResponseV0.success(txData);
    }
}
