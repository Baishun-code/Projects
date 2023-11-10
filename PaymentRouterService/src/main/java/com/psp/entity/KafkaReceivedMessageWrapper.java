package com.psp.entity;

import org.springframework.kafka.support.Acknowledgment;

public class KafkaReceivedMessageWrapper {

    private String message;
    private Acknowledgment acknowledgment;

    public KafkaReceivedMessageWrapper(String message, Acknowledgment acknowledgment) {
        this.message = message;
        this.acknowledgment = acknowledgment;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Acknowledgment getAcknowledgment() {
        return acknowledgment;
    }

    public void setAcknowledgment(Acknowledgment acknowledgment) {
        this.acknowledgment = acknowledgment;
    }
}
