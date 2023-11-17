package com.psp.task;

import com.psp.entity.TfReceivedNotification;
import com.psp.service.MessageSendingHandler;
import com.psp.service.ReceivedNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PendingMessagesQueryingTask {

    @Autowired
    private MessageSendingHandler messageSendingHandler;
    @Autowired
    private ReceivedNotificationService receivedNotificationService;

    @Scheduled(cron = "${tasks.cron-expression}")
    public void onQuerying(){
        List<TfReceivedNotification> allPendingNotifications =
                receivedNotificationService.getAllPendingNotifications();
        for (TfReceivedNotification allPendingNotification : allPendingNotifications) {
            messageSendingHandler.handle(allPendingNotification);
        }
    }
}
