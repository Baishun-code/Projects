package com.psp.controller;

import com.psp.entity.ResponseV0;
import com.psp.entity.TfTransactionInfo;
import com.psp.entity.TxTransactionInfo;
import com.psp.mapper.TfTransactionInfomMapper;
import com.psp.service.MessageService;
import com.psp.service.TXService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.POST;
import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private TXService txService;

    @RequestMapping("/collect")
    public ResponseV0 getAllPendingInfo(){
        List<TxTransactionInfo> txData = messageService.getTxData();
        return ResponseV0.success("return information", txData);
    }

    @RequestMapping("/cnacel")
    public ResponseV0 cancelTxInfo(@Param("serialNums") List<String> serialNums){
        try {
            for (String serialNum : serialNums) {
                txService.deleteFromTxTable(serialNum);
            }
        }catch (Exception e){
            return ResponseV0.fail("Fail to cancel data");
        }

        return ResponseV0.success("Cancel data successfully");
    }

    @PostMapping("/tx/test")
    public ResponseV0 test(@RequestBody List<String> list){
        System.out.println("====================");
        for (String s : list) {
            System.out.println(s);
        }
        return ResponseV0.success("Test");
    }


}
