package com.psp.kafka;

import com.psp.service.KafkaListenerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationKafkaListener {

    @Autowired
    private KafkaListenerHandler kafkaListenerHandler;

    @KafkaListener(topics = "${kafka-listened-topics.notification}")
    public void onListen(@Payload String message,
                         @Header(KafkaHeaders.OFFSET) int index,
                         @Header(KafkaHeaders.ACKNOWLEDGMENT)Acknowledgment acknowledgment){

        kafkaListenerHandler.handle(message, index, acknowledgment);

    }
}
