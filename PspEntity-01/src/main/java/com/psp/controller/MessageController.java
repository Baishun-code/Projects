package com.psp.controller;

import com.psp.entity.ResponseV0;
import com.psp.mapper.TfTransactionInfomMapper;
import com.psp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("/collect")
    public ResponseV0 getAllPendingInfo(){
        List<TfTransactionInfomMapper> txData = messageService.getTxData();
        return ResponseV0.success("return information", txData);
    }
}
