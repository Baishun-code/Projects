package com.psp.task;

import org.springframework.kafka.core.KafkaTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class FetchManager {

    private LinkedBlockingDeque<MessageWrapper> messageObjs;
    private LinkedBlockingDeque<String> callBackMessages;
    private KafkaTemplate<String, String> kafkaTemplate;
    private ConcurrentHashMap<String, MessageWrapper> map;
    private FetchThread fetchThread;
    private CallBackThread callBackThread;

    public FetchManager(KafkaTemplate<String, String> kafkaTemplate){
        callBackMessages = new LinkedBlockingDeque<>();
        this.kafkaTemplate = kafkaTemplate;
        messageObjs = new LinkedBlockingDeque<>();
        map = new ConcurrentHashMap<>();
        new Thread(new FetchThread(messageObjs,
                kafkaTemplate,
                map,
                callBackMessages,
                this)).start();
    }

    public void put(Map<String, Object> dataMap, String topic, String serviceName){
        for (Map.Entry<String, Object> messageBody : dataMap.entrySet()) {
            if(!map.containsKey(messageBody.getKey())){
                MessageWrapper messageWrapper = new MessageWrapper(messageBody.getKey(),
                        topic, messageBody.getValue(), serviceName);
                messageObjs.addFirst(messageWrapper);
                map.put(messageBody.getKey(), messageWrapper);
            }
        }
    }

    public FetchThread getFetchThread() {
        return fetchThread;
    }

    public void setFetchThread(FetchThread fetchThread) {
        this.fetchThread = fetchThread;
    }
}
