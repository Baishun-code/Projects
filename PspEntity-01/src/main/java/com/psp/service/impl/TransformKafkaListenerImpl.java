package com.psp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.psp.common.TxStatus;
import com.psp.entity.TfFinishedTransaction;
import com.psp.service.TransactionInfoService;
import com.psp.service.TransformKafkaListener;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TransformKafkaListenerImpl implements TransformKafkaListener {

    @Autowired
    private TransactionInfoService transactionInfoService;

    @Override
    @KafkaListener(topics = "${kafka-listened-topics.finished-transactions}")
    public void doListenFinishedTransactions(@Payload String message,
                                             @Header(KafkaHeaders.OFFSET) int index,
                                             @Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment) {

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            TfFinishedTransaction tfFinishedTransaction =
                    objectMapper.readValue(message, TfFinishedTransaction.class);

            transactionInfoService.changeTransactionState(tfFinishedTransaction.getSerialNo(),
                    TxStatus.TRANSFERING.code,
                    TxStatus.FINISHED.code,
                    tfFinishedTransaction.getInsertDt());

            //ack the message after updating database
            acknowledgment.acknowledge();
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
