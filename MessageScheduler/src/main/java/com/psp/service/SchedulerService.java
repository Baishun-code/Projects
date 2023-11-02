package com.psp.service;

import com.psp.conf.KafkaMessageService;
import com.psp.entity.ResponseV0;
import com.psp.entity.TxTransactionInfo;
import com.psp.task.FetchManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class SchedulerService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private KafkaMessageService kafkaMessageService;
    @Autowired
    private FetchManager fetchManager;

    @Scheduled(cron = "${spring.task.scheduling.corn}")
    public void doCollectAndSendMessage(){
        log.info("Polling data --------------->");
        fetchManager.pollFromService();
    }
}
