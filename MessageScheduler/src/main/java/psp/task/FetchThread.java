package psp.task;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import psp.util.Util;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;

public class FetchThread implements Runnable{

    private ConcurrentLinkedDeque<MessageWrapper> messageObjs;
    private ConcurrentLinkedDeque<String> callBackMessages;
    private KafkaTemplate<String, String> kafkaTemplate;
    private boolean RUNNING;

    public FetchThread(ConcurrentLinkedDeque<MessageWrapper> messageObjs,
                       KafkaTemplate<String, String> kafkaTemplate,
                       ConcurrentLinkedDeque<String> callBackMessages){
        this.kafkaTemplate = kafkaTemplate;
        this.callBackMessages = callBackMessages;
        this.messageObjs = messageObjs;
        RUNNING = true;
    }

    @Override
    public void run() {
        try {
            while (RUNNING){
                MessageWrapper messageWrapper = messageObjs.pollLast();
                String key = messageWrapper.topic;
                String topic = messageWrapper.topic;
                Object messObj = messageWrapper.messObj;
                String messString = Util.converObjectToJason(messObj);
                ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, key, messString);

                send.addCallback(new SuccessCallback<SendResult<String, String>>() {
                                     @Override
                                     public void onSuccess(SendResult<String, String> stringStringSendResult) {
                                         ProducerRecord<String, String> producerRecord = stringStringSendResult.getProducerRecord();
                                     }
                                 },
                        new FailureCallback() {
                            @Override
                            public void onFailure(Throwable throwable) {

                            }
                        });
            }
        }catch (Exception e){
            new Thread(new FetchThread(messageObjs, kafkaTemplate, callBackMessages));
        }
    }
}
