package com.psp.service;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public interface TransformKafkaListener {

    public void doListenFinishedTransactions(String message,int index, Acknowledgment acknowledgment);
}
