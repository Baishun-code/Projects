package com.psp.kafka;

import com.psp.entity.TfReceivedNotification;
import com.psp.service.KafkaListenerHandler;
import com.psp.service.ReceivedNotificationService;
import com.psp.service.UserInfoService;
import com.psp.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
public class DatabaseKafkaHandler implements KafkaListenerHandler {

    private LinkedBlockingDeque<MessageWrapper> messageQueue;
    private ReceivedNotificationService receivedNotificationService;
    private UserInfoService userInfoService;
    private Thread[] works;
    private int workerSize;
    private boolean RUNNING;

    public DatabaseKafkaHandler(ReceivedNotificationService receivedNotificationService,
                                int workerSize,
                                UserInfoService userInfoService){
        messageQueue = new LinkedBlockingDeque<>();
        this.receivedNotificationService = receivedNotificationService;
        this.workerSize = workerSize;
        RUNNING = true;
        works = new Thread[workerSize];
        this.userInfoService = userInfoService;
        startWorkers();
    }

    private void startWorkers(){
        for (int i = 0; i < works.length; i++) {
            works[i] = new Thread(new Worker(messageQueue, receivedNotificationService,
                    works, i, userInfoService));
            works[i].setName("Kafka-listener-worker-thread-".concat(String.valueOf(i)));
            works[i].start();
        }
    }

    @Override
    public void handle(String message, int index, Acknowledgment acknowledgment) {
        messageQueue.add(new MessageWrapper(message, acknowledgment));
    }

    static class Worker implements Runnable{
        private LinkedBlockingDeque<MessageWrapper> messageQueue;
        private ReceivedNotificationService receivedNotificationService;
        private UserInfoService userInfoService;
        private Thread[] works;
        private int workerIndex;
        private boolean RUNNING;

        public Worker(LinkedBlockingDeque<MessageWrapper> messageQueue,
                      ReceivedNotificationService receivedNotificationService,
                      Thread[] works,
                      int workerIndex,
                      UserInfoService userInfoService) {
            this.messageQueue = messageQueue;
            this.receivedNotificationService = receivedNotificationService;
            this.works = works;
            this.workerIndex = workerIndex;
            this.userInfoService = userInfoService;
        }

        @Override
        public void run() {
            while (RUNNING){
                try {
                    doWork();
                }catch (Exception e){
                    log.error(e.getMessage());
                }

            }
        }

        private void doWork() throws InterruptedException {
            //(1) poll message from blocking queue
            MessageWrapper message = messageQueue.take();

            //(2) decode the message to its Object
            TfReceivedNotification tfReceivedNotification =
                    Util.decodeObj(message.getMessageBody(), TfReceivedNotification.class);

//            String userId = tfReceivedNotification.getUserId();
//            String type = tfReceivedNotification.getNotiType();
//            TdUserContactInfo userInfo = userInfoService.getUserInfo(userId, type);

            tfReceivedNotification.setNotiStatus("0");
            tfReceivedNotification.setInsertDt(new Date());

            //(3) try writing it into database
            receivedNotificationService.writeNotificationRecord(tfReceivedNotification);

            //(4) ack the message
            message.ack.acknowledge();
        }
    }

    static class MessageWrapper{
        private String messageBody;
        private Acknowledgment ack;

        public MessageWrapper(String messageBody, Acknowledgment ack) {
            this.messageBody = messageBody;
            this.ack = ack;
        }

        public String getMessageBody() {
            return messageBody;
        }

        public void setMessageBody(String messageBody) {
            this.messageBody = messageBody;
        }

        public Acknowledgment getAck() {
            return ack;
        }

        public void setAck(Acknowledgment ack) {
            this.ack = ack;
        }
    }
}
