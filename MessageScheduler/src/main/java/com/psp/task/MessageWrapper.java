package com.psp.task;

public class MessageWrapper {

    public String key;
    public Object messObj;
    public String topic;
    public String serviceName;

    public MessageWrapper(String k, String topic, Object messObj, String serviceName){
        this.key = k;
        this.topic = topic;
        this.messObj = messObj;
        this.serviceName = serviceName;
    }
}
