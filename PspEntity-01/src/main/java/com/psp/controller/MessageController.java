package com.psp.controller;

import com.psp.entity.ResponseV0;
import com.psp.entity.TfTransactionInfo;
import com.psp.entity.TxPendingNotification;
import com.psp.entity.TxTransactionInfo;
import com.psp.mapper.TfTransactionInfomMapper;
import com.psp.service.MessageService;
import com.psp.service.NotificationService;
import com.psp.service.TXService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.POST;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class MessageController {

    @Autowired
    private TXService txService;
    @Autowired
    private NotificationService notificationService;

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

    @RequestMapping("/noti/collect")
    public ResponseV0 getAllPendingNotifications(){
        HashMap<String, Object> map = new HashMap<>();
        try {
            List<TxPendingNotification> txPendingNotifications = notificationService.pollAllPendingMessage();
            for (int i = 0; i < txPendingNotifications.size(); i++) {
                TxPendingNotification currPendingNotifications
                        = txPendingNotifications.get(i);
                map.put(String.valueOf(currPendingNotifications.getSerialNo()), currPendingNotifications);
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseV0.fail("Fail to fetch data");
        }
        return ResponseV0.success(map);
    }

    @PostMapping("/noti/cancel")
    public ResponseV0 cancelPendingNotification(@RequestBody List<String> serialNos){
        log.info("Receiving serialNo {}", serialNos);
        try {
            for (String serialNo : serialNos) {
                notificationService.cancelMessage(Integer.valueOf(serialNo));
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseV0.fail("Fail to cancel data");
        }
        return ResponseV0.success("Data cancelled");
    }
}
