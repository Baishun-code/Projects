package com.psp.controller;

import com.psp.entity.ResponseV0;
import com.psp.entity.TfFinishedTransaction;
import com.psp.service.FinishedTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class MessageController {

    @Autowired
    private FinishedTransactionService finishedTransactionService;

    @RequestMapping("/collect")
    public ResponseV0 collect(){
        HashMap<String, Object> map = new HashMap<>();

        List<TfFinishedTransaction> tfFinishedTransactions =
                finishedTransactionService.fetchTransaction();

        for (TfFinishedTransaction tfFinishedTransaction : tfFinishedTransactions) {
            map.put(tfFinishedTransaction.getSerialNo(), tfFinishedTransaction);
        }

        return ResponseV0.success(map);
    }


    @PostMapping("/cancel")
    public ResponseV0 cancel(@RequestBody List<String> serialNums){
        try{
            for (int i = 0; i < serialNums.size(); i++) {
                finishedTransactionService.cancelTransaction(serialNums.get(i));
            }
        }catch (Exception e){
            log.error(e.getMessage());
            ResponseV0.fail("Failed to cancel data");
        }
        return ResponseV0.success("Message canceled ...");
    }
}
