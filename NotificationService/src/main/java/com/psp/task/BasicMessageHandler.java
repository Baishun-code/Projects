package com.psp.task;

import com.psp.entity.EmailMessage;
import com.psp.entity.TdUserContactInfo;
import com.psp.entity.TfReceivedNotification;
import com.psp.message.MailSender;
import com.psp.service.MessageSendingHandler;
import com.psp.service.ReceivedNotificationService;
import com.psp.service.UserInfoService;

import java.util.HashSet;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BasicMessageHandler implements MessageSendingHandler {

    private UserInfoService userInfoService;
    private ThreadPoolExecutor threadPoolExecutor;
    private ReceivedNotificationService receivedNotificationService;
    private MessageFactory messageFactory;
    //record the messages that are in handling,
    //to avoid the same message is sent for multiple time
    private HashSet<Integer> onHandlingMessages;
    private MailSender mailSender;
    private int size;

    public BasicMessageHandler(UserInfoService userInfoService,
                               int size,
                               MailSender mailSender,
                               ReceivedNotificationService receivedNotificationService,
                               MessageFactory messageFactory){
        this.receivedNotificationService = receivedNotificationService;
        this.userInfoService = userInfoService;
        this.messageFactory = messageFactory;
        onHandlingMessages = new HashSet<>();
        this.size = size;
        this.mailSender = mailSender;
        threadPoolExecutor = new ThreadPoolExecutor(size,
                size,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque());
    }



    @Override
    public void handle(TfReceivedNotification tfReceivedNotification) {
        int serialNo = tfReceivedNotification.getSerialNo();
        //if the message is currently on handling, do nothing
        if(!onHandlingMessages.contains(serialNo)){
            onHandlingMessages.add(serialNo);

            MessageTask messageTask = new MessageTask(mailSender,
                    userInfoService,
                    receivedNotificationService,
                    tfReceivedNotification,
                    messageFactory,
                    onHandlingMessages);

            threadPoolExecutor.execute(messageTask);
        }
    }

    static class MessageTask implements Runnable{
        private MailSender mailSender;
        private UserInfoService userInfoService;
        private ReceivedNotificationService receivedNotificationService;
        private TfReceivedNotification tfReceivedNotification;
        private MessageFactory messageFactory;
        private HashSet<Integer> onHandlingMessages;


        public MessageTask(MailSender mailSender,
                           UserInfoService userInfoService,
                           ReceivedNotificationService receivedNotificationService,
                           TfReceivedNotification tfReceivedNotification,
                           MessageFactory messageFactory,
                           HashSet<Integer> onHandlingMessages) {
            this.mailSender = mailSender;
            this.userInfoService = userInfoService;
            this.receivedNotificationService = receivedNotificationService;
            this.tfReceivedNotification = tfReceivedNotification;
            this.messageFactory = messageFactory;
            this.onHandlingMessages = onHandlingMessages;
        }

        @Override
        public void run() {
            try {
                doWork();
            }catch (Exception e){
                e.getMessage();
            }
        }

        private void doWork(){
            //(1) get user information and notification content
            String userId = tfReceivedNotification.getAcctId();
            String type = tfReceivedNotification.getNotiType();
//            //send e-mail by default
//            if(type == null){
//                type = "0";
//            }
            TdUserContactInfo userInfo = userInfoService.getUserInfo(userId, type);
            //(2) create message use messageFactory
            EmailMessage emailMessage =
                    (EmailMessage) messageFactory.buildMessage(tfReceivedNotification, userInfo);
            //(3) send message
            String[] targetEmail = new String[]{userInfo.getInfoDetail()};
            mailSender.sendMessage(targetEmail, emailMessage.getSubject(), emailMessage.getText());
            //(4) cancel the message from database
            receivedNotificationService.cancelNotification(tfReceivedNotification.getSerialNo());
            //(5) removing the message from on handling messages queue to free space
            onHandlingMessages.remove(tfReceivedNotification.getSerialNo());
        }
    }
}
