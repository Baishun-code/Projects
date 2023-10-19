package psp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import psp.entity.ResponseV0;
import psp.entity.TxTransactionInfo;

import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "${spring.task.scheduling.corn}")
    public void doCollectAndSendMessage(){
        ResponseV0 forObject = restTemplate.getForObject("http://service-payment/collect", ResponseV0.class);
//        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://service-payment/test", String.class);
//        String body = forEntity.getBody();
//        System.out.println(body);
        List<TxTransactionInfo> list = (List<TxTransactionInfo>) forObject.response;
        list.forEach(System.out::println);
        System.out.println("------------------------");
    }
}
