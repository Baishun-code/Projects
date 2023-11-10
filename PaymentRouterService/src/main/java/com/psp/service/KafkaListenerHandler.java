package com.psp.service;

import org.springframework.kafka.support.Acknowledgment;

public interface KafkaListenerHandler {

    void handle(String message, int index, Acknowledgment acknowledgment);
}
