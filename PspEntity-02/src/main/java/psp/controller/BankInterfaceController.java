package psp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import psp.entity.ResponseV0;
import psp.entity.TxTransactionInfo;
import psp.service.MessageService;

import javax.xml.ws.soap.Addressing;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/interbank")
@Slf4j
public class BankInterfaceController {

    @Autowired
    private MessageService messageService;

    @Value("${kafka-listened-topics.transaction-from-other-banks}")
    private String transferTopic;

    @PostMapping("/tranfer")
    public ResponseV0 transfer(@RequestBody String txTransactionInfo){
        log.info("Interbank transfering requests coming in ....");
        boolean received = false;
        try {
            messageService.sendTo(txTransactionInfo, transferTopic);
            received = true;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(received){
            return ResponseV0.success("Transfer received ..");
        }else {
            return ResponseV0.fail("Failed to receive message");
        }
    }
}
