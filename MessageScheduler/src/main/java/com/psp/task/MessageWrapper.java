package com.psp.task;

public class MessageWrapper {

    public String topic;
    public String serviceName;
    public String reqUrl;

    public MessageWrapper(String topic, String serviceName,String reqUrl){
        this.topic = topic;
        this.reqUrl = reqUrl;
        this.serviceName = serviceName;
    }
}
