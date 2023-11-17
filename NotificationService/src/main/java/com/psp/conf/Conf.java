package com.psp.conf;

import com.psp.kafka.DatabaseKafkaHandler;
import com.psp.message.MailSender;
import com.psp.service.KafkaListenerHandler;
import com.psp.service.MessageSendingHandler;
import com.psp.service.ReceivedNotificationService;
import com.psp.service.UserInfoService;
import com.psp.task.BasicMessageHandler;
import com.psp.task.MessageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Conf {

    @Bean
    public MailSender mailSender(@Value("${mail-sender.host}") String host,
                                 @Value("${mail-sender.username}") String userName,
                                 @Value("${mail-sender.password}") String password){
        return new MailSender(host, userName, password);
    }

    @Bean
    public MessageSendingHandler messageSendingHandler(UserInfoService userInfoService,
                                                       MailSender mailSender,
                                                       ReceivedNotificationService receivedNotificationService,
                                                       MessageFactory messageFactory,
                                                       @Value("${tasks.core-pool-size}") String size){
        Integer corePoolSize = Integer.valueOf(size);
        return new BasicMessageHandler(userInfoService, corePoolSize, mailSender, receivedNotificationService, messageFactory);
    }

    @Bean
    public KafkaListenerHandler kafkaListenerHandler(ReceivedNotificationService receivedNotificationService,
                                                     UserInfoService userInfoService,
                                                     @Value("${kafka-listener-handler.worker-threads}") int workerSize){
        return new DatabaseKafkaHandler(receivedNotificationService, workerSize, userInfoService);
    }



}
