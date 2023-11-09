package com.psp.kafka;


import com.psp.service.KafkaListenerHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.concurrent.ConcurrentLinkedDeque;

public class PayKafkaListener {

    private KafkaListenerHandler kafkaListenerHandler;


    public PayKafkaListener(){

    }

    @KafkaListener(topics = "payment-interbank-router")
    private void onListening(@Payload String message,
                             @Header(KafkaHeaders.OFFSET) int offset,
                             @Header(KafkaHeaders.ACKNOWLEDGMENT)Acknowledgment acknowledgment){

        kafkaListenerHandler.handle(message);

    }

}
