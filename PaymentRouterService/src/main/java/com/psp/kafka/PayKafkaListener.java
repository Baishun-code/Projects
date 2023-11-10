package com.psp.kafka;


import com.psp.service.KafkaListenerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class PayKafkaListener {

    @Autowired
    private KafkaListenerHandler kafkaListenerHandler;

    @KafkaListener(topics = "payment-interbank-router")
    private void onListening(@Payload String message,
                             @Header(KafkaHeaders.OFFSET) int offset,
                             @Header(KafkaHeaders.ACKNOWLEDGMENT)Acknowledgment acknowledgment){

        kafkaListenerHandler.handle(message, offset, acknowledgment);

    }

}
