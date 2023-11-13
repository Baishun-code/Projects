package psp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import psp.service.MessageService;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public boolean sendTo(String message, String topic) {
        boolean sendResult = false;
        try {
            ListenableFuture<SendResult<String, String>> send
                    = kafkaTemplate.send(topic, message);
            SendResult<String, String> result = send.get();
            sendResult = true;
        } catch (ExecutionException e) {
            log.error(e.getMessage());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        return sendResult;
    }
}
