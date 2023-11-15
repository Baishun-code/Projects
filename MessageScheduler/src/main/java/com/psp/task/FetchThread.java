package com.psp.task;

import com.psp.entity.ResponseV0;
import com.psp.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
public class FetchThread implements Runnable{

    private ConcurrentHashMap<String, MessageWrapper> map;
    private LinkedBlockingDeque<MessageWrapper> messageObjs;
    private LinkedBlockingDeque<String> callBackMessages;
    private KafkaTemplate<String, String> kafkaTemplate;
    private FetchManagerService fetchManager;
    private RestTemplate restTemplate;
    private boolean RUNNING;

    public FetchThread(LinkedBlockingDeque<MessageWrapper> messageObjs,
                       KafkaTemplate<String, String> kafkaTemplate,
                       ConcurrentHashMap<String, MessageWrapper> map,
                       LinkedBlockingDeque<String> callBackMessages,
                       RestTemplate restTemplate,
                       FetchManagerService fetchManager){
        this.kafkaTemplate = kafkaTemplate;
        this.callBackMessages = callBackMessages;
        fetchManager.setFetchThread(this);
        this.messageObjs = messageObjs;
        this.restTemplate = restTemplate;
        this.map = map;
        RUNNING = true;
    }

    @Override
    public void run() {
        try {
            while (RUNNING){
                MessageWrapper messageWrapper = messageObjs.take();
                if(messageWrapper == null){
                    continue;
                }
                //fetch data in sync mode
                log.info("Fetching data through url: {}", messageWrapper.reqUrl);
                ResponseEntity<ResponseV0> forEntity = null;
                try {
                    forEntity =
                            restTemplate.getForEntity(messageWrapper.reqUrl, ResponseV0.class);
                }catch (Exception e){
                    log.error(e.getMessage());
                }

                if(forEntity == null){
                    continue;
                }

                ResponseV0 responseV0 = forEntity.getBody();
                log.info("Response state: {}", responseV0.response);

                Map<String, Object> messMap =  Util.retrectMessageFromReponse(responseV0);
                if("200".equals(responseV0.code) && messMap.size() > 0){

                    String topic = messageWrapper.topic;

                    for (Map.Entry<String, Object> messageEntry : messMap.entrySet()) {
                        //if the data has already in map, discard the message
                        if(!map.contains(messageEntry.getKey())){
                            map.put(messageEntry.getKey(), messageWrapper);
                            //convert message object to JSON String
                            String messageStr = Util.converObjectToJason(messageEntry.getValue());
                            //commit to kafka in async mode, and add call back function
                            //for future process
                            ListenableFuture<SendResult<String, String>> send
                                    = kafkaTemplate.send(topic, messageEntry.getKey(), messageStr);
                            send.addCallback(new SuccCallBack(callBackMessages),
                                    new FailCallBack(map, messageEntry.getKey()));
                        }
                    }
                }else if(messMap.size() == 0 || messMap == null){
                    //if nothing is collected through the interface,
                    // do nothing
                  continue;
                } else {
                    //if failed to fetch data, the messageWrapper will be put
                    //back to messageObjs, so that it can be reprocessed
                    messageObjs.addFirst(messageWrapper);
                }
            }
        }catch (Exception e){
            //if there is any exception forcing current thread to stop,
            //start another thread to replace current one
            System.out.println("Start new Thread ...");
            new Thread(
                    new FetchThread(messageObjs,
                    kafkaTemplate,
                    map,
                    callBackMessages,
                    restTemplate,
                    fetchManager));
        }
    }

    public void shutdown(){
        messageObjs.clear();
        callBackMessages.clear();
        RUNNING = false;
    }

    /**
     * call back class for handing failure when sending
     * message to kafka, remove the data from map,
     * so it can be processed again later
     */
    static class FailCallBack implements  FailureCallback{

        private ConcurrentHashMap<String, MessageWrapper> map;
        private String key;

        public FailCallBack(ConcurrentHashMap<String, MessageWrapper> map,
                            String key){
            this.map = map;
            this.key = key;
        }

        @Override
        public void onFailure(Throwable ex) {
            map.remove(key);
        }
    }

    /**
     * if success, putting message into callBackMessages, waiting for
     * callback threads to call original services to cancel the data
     * in tx table
     */
    static class SuccCallBack implements SuccessCallback<SendResult<String, String>>{

        private LinkedBlockingDeque<String> callBackMessages;

        public SuccCallBack(LinkedBlockingDeque<String> callBackMessages){
            this.callBackMessages = callBackMessages;
        }

        @Override
        public void onSuccess(SendResult<String, String> result) {
            ProducerRecord<String, String> producerRecord = result.getProducerRecord();
            String key = producerRecord.key();
            callBackMessages.addFirst(key);
        }
    }




}
