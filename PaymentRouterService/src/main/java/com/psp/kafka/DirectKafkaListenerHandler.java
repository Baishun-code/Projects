package com.psp.kafka;

import com.psp.mapper.TfFinishedTransactionMapper;
import com.psp.service.BankInfoService;
import com.psp.service.FinishedTransactionService;
import com.psp.util.Util;
import com.psp.entity.*;
import com.psp.service.CacheService;
import com.psp.service.KafkaListenerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Receive message from kafka, and send it directly to other bank service
 * without logging record into database for demonstration purpose
 */
@Slf4j
public class DirectKafkaListenerHandler implements KafkaListenerHandler {

    private LinkedBlockingDeque<KafkaReceivedMessageWrapper> queue;
    private Thread[] workerQueue;
    private RestTemplate restTemplate;
    private CacheService cacheService;
    private FinishedTransactionService mapper;
    private BankInfoService bankInfoService;

    public DirectKafkaListenerHandler(int size,
                                      CacheService cacheService,
                                      FinishedTransactionService mapper,
                                      BankInfoService bankInfoService) {
        workerQueue = new Thread[size];
        queue = new LinkedBlockingDeque<>();
        restTemplate = new RestTemplate();
        this.cacheService = cacheService;
        this.mapper = mapper;
        this.bankInfoService = bankInfoService;
        startWorker();
    }

    private void startWorker(){
        for (int i = 0; i < workerQueue.length; i++) {
            log.info("Start kafka worker thread {}",i);
            workerQueue[i] = new Thread(new Worker(i, workerQueue, queue, restTemplate, cacheService, mapper, bankInfoService));
            workerQueue[i].setName("Kafka-Worker-Thread-".concat(String.valueOf(i)));
            workerQueue[i].start();
        }
    }

    @Override
    public void handle(String message, int index, Acknowledgment acknowledgment) {
        queue.addFirst(new KafkaReceivedMessageWrapper(message, acknowledgment));
    }

    static class Worker implements Runnable{

        private int workIndex;
        private Thread[] workerQueue;
        private boolean RUNNING;
        private LinkedBlockingDeque<KafkaReceivedMessageWrapper> queue;
        private RestTemplate restTemplate;
        private CacheService cacheService;
        private FinishedTransactionService mapper;
        private BankInfoService bankInfoService;

        public Worker(int workIndex,
                      Thread[] workerQueue,
                      LinkedBlockingDeque<KafkaReceivedMessageWrapper> queue,
                      RestTemplate restTemplate,
                      CacheService cacheService,
                      FinishedTransactionService mapper,
                      BankInfoService bankInfoService) {
            this.workIndex = workIndex;
            this.workerQueue = workerQueue;
            this.queue = queue;
            this.restTemplate = restTemplate;
            RUNNING = true;
            this.cacheService = cacheService;
            this.mapper = mapper;
            this.bankInfoService = bankInfoService;
        }

        @Override
        public void run() {
            while(RUNNING){
                try {
                    doSendMessage();
                }catch (Exception e){
                    log.error(e.getMessage());
                    log.info("Start new thread");
                    workerQueue[workIndex] = new Thread(new Worker(workIndex,
                            workerQueue, queue, restTemplate, cacheService, mapper, bankInfoService));
                    workerQueue[workIndex].setName(Thread.currentThread().getName());
                }
            }
        }

        private void doSendMessage() throws InterruptedException {
            KafkaReceivedMessageWrapper messageWrapper = queue.take();
            //get original message
            TxTransactionInfo txTransactionInfo =
                    Util.decodeObj(messageWrapper.getMessage(), TxTransactionInfo.class);

            //if the message is incomplete, remove the message from MQ
            if(txTransactionInfo == null){
                messageWrapper.getAcknowledgment().acknowledge();
                return;
            }
            //get bank id
            String targetAcctBank = txTransactionInfo.getTargetAcctBank();
            //get target bank info
            TdBankMessageInfo tdBankMessageInfo =
                    (TdBankMessageInfo) cacheService.get(targetAcctBank);

            //if there is no record in cache, get from database directly
            if(tdBankMessageInfo == null){
                tdBankMessageInfo = bankInfoService.queryBankInfo(targetAcctBank);
                cacheService.put(targetAcctBank, tdBankMessageInfo);
            }

            //bank information
            String ip = tdBankMessageInfo.getBankIp();
            String port = tdBankMessageInfo.getBankPort();
            String url = tdBankMessageInfo.getBankUrl();

            String requestUrl = Util.assembleUrl(ip, port, url);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity httpEntity = new HttpEntity<TxTransactionInfo>(txTransactionInfo, headers);
            try {
                ResponseEntity<ResponseV0> responseV0Entity =
                        restTemplate.postForEntity(requestUrl, httpEntity, ResponseV0.class);
                ResponseV0 body = responseV0Entity.getBody();
                if("200".equals(body.code)){
                    //if data is sent successfully, commit the message, if failed neglect the data
                    //for reprocessing
                    TfFinishedTransaction tfFinishedTransaction =
                            new TfFinishedTransaction(txTransactionInfo.getSerialNo(), new Date());

                    mapper.createNewFinishedTransac(tfFinishedTransaction);
                    //if insert local storage successful, then ack the data
                    Acknowledgment acknowledgment = messageWrapper.getAcknowledgment();
                    acknowledgment.acknowledge();
                    log.info("Message sent successfully ...");
                }
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }
}
