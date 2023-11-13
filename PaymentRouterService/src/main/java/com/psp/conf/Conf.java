package com.psp.conf;

import com.psp.kafka.DatabaseKafkaListenerHandler;
import com.psp.kafka.DirectKafkaListenerHandler;
import com.psp.service.CacheService;
import com.psp.service.KafkaListenerHandler;
import com.psp.service.TxService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Conf {


    @Value("${kafka-listener-handler.worker-threads}")
    private int threadCount;

    @Bean
    public KafkaListenerHandler kafkaListenerHandler(CacheService cacheService){
        return new DirectKafkaListenerHandler(threadCount, cacheService);
    }
}
