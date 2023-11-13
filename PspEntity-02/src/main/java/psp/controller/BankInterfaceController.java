package psp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import psp.entity.ResponseV0;
import psp.entity.TxTransactionInfo;
import psp.service.MessageService;

import javax.xml.ws.soap.Addressing;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/interbank")
public class BankInterfaceController {

    @Autowired
    private MessageService messageService;
    @Value("")
    private String transferTopic;

    @RequestMapping(value = "/tranfer", method = RequestMethod.POST)
    public ResponseV0 trnafer(@RequestBody String txTransactionInfo){
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
