package com.psp.service;

import com.psp.entity.TfReceivedNotification;

import java.util.List;

public interface ReceivedNotificationService {

    void writeNotificationRecord(TfReceivedNotification tfReceivedNotification);

    List<TfReceivedNotification> getAllPendingNotifications();

    void cancelNotification(Integer serialNo);

    void removePendingMessage(String serialNo);
}
