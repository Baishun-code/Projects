package com.psp.conf;

import com.psp.service.ScheduleNameService;
import com.psp.task.FetchManager;
import com.psp.task.FetchThread;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@Configuration
public class Config {

    @Value("${fetch-manager.fetch-size}")
    private int fetchSize;
    @Value("${fetch-manager.callback-size}")
    private int callbackSize;
    @Value("${fetch-manager.batch-size}")
    private int batchSize;
    @Value("${fetch-manager.use-batch}")
    private boolean useBatch;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public FetchManager fetchManager(KafkaTemplate<String, String> kafkaTemplate,
                                     ScheduleNameService scheduleNameService,
                                     RestTemplate restTemplate){
        return new FetchManager(kafkaTemplate,
                scheduleNameService,
                restTemplate,
                fetchSize,
                callbackSize,
                useBatch,
                batchSize);
    }

}
