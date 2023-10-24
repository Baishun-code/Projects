package psp.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import psp.util.Util;

import org.springframework.kafka.support.SendResult;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaMessageService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String key, Object o) throws ExecutionException, InterruptedException {
        String value = Util.converObjectToJason(o);
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, key, value);
        SendResult<String, String> stringStringSendResult = send.get();
    }
}