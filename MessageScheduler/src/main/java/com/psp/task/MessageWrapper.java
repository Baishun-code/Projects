package com.psp.task;

/**
 * Store kafka topic, service name and corresponding request url
 */
public class MessageWrapper {

    public String topic;
    public String serviceName;
    public String reqUrl;
    public String callBackUrl;

    public MessageWrapper(String topic, String serviceName,String reqUrl,String callBackUrl){
        this.topic = topic;
        this.reqUrl = reqUrl;
        this.serviceName = serviceName;
    }

    public MessageWrapper(String topic, String serviceName) {
        this.topic = topic;
        this.serviceName = serviceName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }
}
