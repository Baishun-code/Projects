package com.psp.service;

import java.util.List;

public interface ReceivedNotificationService {

    void writeNotificationRecord(TfReceivedNotification tfReceivedNotification);

    List<TfReceivedNotification> getAllPendingNotifications();

    void cancelNotification(String serialNo);

    void removePendingMessage(String serialNo);
}
