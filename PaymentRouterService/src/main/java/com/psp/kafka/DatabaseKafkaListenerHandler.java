package com.psp.kafka;

import com.psp.entity.TxTransactionInfo;
import com.psp.service.KafkaListenerHandler;
import com.psp.service.TxService;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * store data into database to accomplish the data receiving process
 */
public class DatabaseKafkaListenerHandler implements KafkaListenerHandler {

    private TxService txService;
    private ConcurrentLinkedDeque<String> messageQueue;

    public DatabaseKafkaListenerHandler(){

    }

    @Override
    public void handle(String message) {
        messageQueue.offer(message);
    }

    static class Worker{

    }

    /**
     *
     * @param txTransactionInfo
     * Store txTransactionInfo into tx table,
     * since this is a local database transaction, once kafka has committed
     * on received data, the data has already stored into database
     */
    private void storeToDatabase(TxTransactionInfo txTransactionInfo){

    }
}
