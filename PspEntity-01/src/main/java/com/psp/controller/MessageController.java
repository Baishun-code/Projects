package com.psp.controller;

import com.psp.entity.ResponseV0;
import com.psp.entity.TfTransactionInfo;
import com.psp.entity.TxTransactionInfo;
import com.psp.mapper.TfTransactionInfomMapper;
import com.psp.service.MessageService;
import com.psp.service.TXService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.POST;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class MessageController {

    @Autowired
    private TXService txService;

    @RequestMapping("/collect")
    public ResponseV0 getAllPendingInfo(){
        Map<String, TxTransactionInfo> txData = txService.queryAllTxRecords();
        return ResponseV0.success("return information", txData);
    }

    @PostMapping("/cancel")
    public ResponseV0 cancelTxInfo(@RequestBody List<String> serialNums){
        try {
            for (String serialNum : serialNums) {
                log.info("Receiving serial data {}", serialNum);
                txService.deleteFromTxTable(serialNum);
            }
        }catch (Exception e){
            return ResponseV0.fail("Fail to cancel data");
        }

        return ResponseV0.success("Cancel data successfully");
    }

}
