package com.psp.service;

public interface KafkaListenerHandler {

    void handle(String message);
}
