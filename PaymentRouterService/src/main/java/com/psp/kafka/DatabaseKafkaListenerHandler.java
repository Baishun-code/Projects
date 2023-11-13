package com.psp.kafka;

import com.psp.util.Util;
import com.psp.entity.KafkaReceivedMessageWrapper;
import com.psp.entity.TxTransactionInfo;
import com.psp.service.KafkaListenerHandler;
import com.psp.service.TxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.Acknowledgment;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * store data into database to accomplish the data receiving process
 */
@Slf4j
public class DatabaseKafkaListenerHandler implements KafkaListenerHandler {

    private TxService txService;
    private Thread[] workerQueue;
    private ConcurrentLinkedDeque<KafkaReceivedMessageWrapper> messageQueue;


    public DatabaseKafkaListenerHandler(TxService txService,
                                        int queueSize) {
        this.txService = txService;
        this.workerQueue = new Thread[queueSize];
        this.messageQueue = new ConcurrentLinkedDeque<KafkaReceivedMessageWrapper>();
    }

    private void startWorker(){
        for (int i = 0; i < workerQueue.length; i++) {
            log.info("Start kafka worker thread {}",i);
            workerQueue[i] = new Thread(new Worker(i, workerQueue, messageQueue, txService));
            workerQueue[i].setName("Kafka-Worker-Thread-".concat(String.valueOf(i)));
        }
    }

    @Override
    public void handle(String message, int index, Acknowledgment acknowledgment) {
        messageQueue.offer(new KafkaReceivedMessageWrapper(message, acknowledgment));
    }

    @Slf4j
    static class Worker implements Runnable{
        private int workIndex;
        private Thread[] workerQueue;
        private boolean RUNNING;
        private ConcurrentLinkedDeque<KafkaReceivedMessageWrapper> messageQueue;
        private TxService txService;

        public Worker(int workIndex,
                      Thread[] workerQueue,
                      ConcurrentLinkedDeque<KafkaReceivedMessageWrapper> messageQueue,
                      TxService txService) {
            this.workIndex = workIndex;
            this.workerQueue = workerQueue;
            this.messageQueue = messageQueue;
            this.txService = txService;
        }

        @Override
        public void run() {
            while (RUNNING){
                try {
                    doWork();
                }catch (Exception e){
                    log.error(e.getMessage());
                    workerQueue[workIndex] =
                            new Thread(new Worker(workIndex,
                                    workerQueue,
                                    messageQueue,
                                    txService));
                }
            }
        }

        /**
         * looping worker, fetching data from messageQueue
         * ack the message after storing into database
         */
        private void doWork(){
            KafkaReceivedMessageWrapper messageWrapper = messageQueue.poll();
            TxTransactionInfo txTransactionInfo =
                    Util.decodeObj(messageWrapper.getMessage(), TxTransactionInfo.class);
            storeToDatabase(txTransactionInfo);
            //message ack, if there is something wrong on writing to database
            //this step won't be executed
            messageWrapper.getAcknowledgment().acknowledge();

        }

        /**
         *
         * @param txTransactionInfo
         * Store txTransactionInfo into tx table,
         * since this is a local database transaction, once kafka has committed
         * on received data, the data has already stored into database
         */
        private void storeToDatabase(TxTransactionInfo txTransactionInfo){
            txService.insertTx(txTransactionInfo);
        }

    }


}
