package psp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import psp.entity.ResponseV0;

@Service
public class SchedulerService {

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "${spring.task.scheduling.corn}")
    public void doCollectAndSendMessage(){
        ResponseEntity<ResponseV0> forEntity = restTemplate.getForEntity("http://", ResponseV0.class);
        ResponseV0 body = forEntity.getBody();
        System.out.println("------------------------");
    }
}
