package com.psp.conf;

import com.psp.service.ScheduleNameService;
import com.psp.task.FetchManagerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

//    @Value("${fetch-manager.fetch-size}")
//    private int fetchSize;
//    @Value("${fetch-manager.callback-size}")
//    private int callbackSize;
//    @Value("${fetch-manager.batch-size}")
//    private int batchSize;
//    @Value("${fetch-manager.use-batch}")
//    private boolean useBatch;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public FetchManagerService fetchManager(KafkaTemplate<String, String> kafkaTemplate,
                                            ScheduleNameService scheduleNameService,
                                            RestTemplate restTemplate,
                                            @Value("${fetch-manager.fetch-size}") int fetchSize,
                                            @Value("${fetch-manager.callback-size}") int callbackSize,
                                            @Value("${fetch-manager.batch-size}") int batchSize,
                                            @Value("${fetch-manager.use-batch}") boolean useBatch){
        return new FetchManagerService(kafkaTemplate,
                scheduleNameService,
                restTemplate,
                fetchSize,
                callbackSize,
                useBatch,
                batchSize);
    }

}
