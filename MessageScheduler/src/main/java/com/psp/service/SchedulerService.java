package com.psp.service;

import com.psp.conf.KafkaMessageService;
import com.psp.task.FetchManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class SchedulerService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private KafkaMessageService kafkaMessageService;
    @Autowired
    private FetchManagerService fetchManager;

    @Scheduled(cron = "${spring.task.scheduling.corn}")
    public void doCollectAndSendMessage(){
        log.info("Polling data --------------->");
        fetchManager.pollFromService();
    }
}
