package psp.task;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import psp.util.Util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class FetchThread implements Runnable{

    private ConcurrentHashMap<String, MessageWrapper> map;
    private LinkedBlockingDeque<MessageWrapper> messageObjs;
    private LinkedBlockingDeque<String> callBackMessages;
    private KafkaTemplate<String, String> kafkaTemplate;
    private FetchManager fetchManager;
    private boolean RUNNING;

    public FetchThread(LinkedBlockingDeque<MessageWrapper> messageObjs,
                       KafkaTemplate<String, String> kafkaTemplate,
                       ConcurrentHashMap<String, MessageWrapper> map,
                       LinkedBlockingDeque<String> callBackMessages,
                       FetchManager fetchManager){
        this.kafkaTemplate = kafkaTemplate;
        this.callBackMessages = callBackMessages;
        fetchManager.setFetchThread(this);
        this.messageObjs = messageObjs;
        this.map = map;
        RUNNING = true;
    }

    @Override
    public void run() {
        try {
            while (RUNNING){
                MessageWrapper messageWrapper = messageObjs.take();
                String topic = messageWrapper.topic;
                String messageStr = Util.converObjectToJason(messageWrapper.messObj);
                String key = messageWrapper.key;
                ListenableFuture<SendResult<String, String>> send
                        = kafkaTemplate.send(topic, key, messageStr);
                send.addCallback(new SuccCallBack(callBackMessages),
                        new FailCallBack(messageObjs, messageWrapper));
            }
        }catch (Exception e){
            //if there is any exception forcing current thread to stop,
            //start another thread to replace current one
            new Thread(new FetchThread(messageObjs, kafkaTemplate, map, callBackMessages, fetchManager));
        }
    }

    public void shutdown(){
        messageObjs.clear();
        callBackMessages.clear();
        RUNNING = false;
    }

    /**
     * call back class for handing failure when sending
     * message to kafka, put the message back to the messageObjs
     * for handing again
     */
    static class FailCallBack implements  FailureCallback{

        private LinkedBlockingDeque<MessageWrapper> messageObjs;
        private MessageWrapper messageWrapper;

        public FailCallBack(LinkedBlockingDeque<MessageWrapper> messageObjs,
                            MessageWrapper messageWrapper){
            this.messageObjs = messageObjs;
            this.messageWrapper = messageWrapper;
        }

        @Override
        public void onFailure(Throwable ex) {
            messageObjs.addFirst(messageWrapper);
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
