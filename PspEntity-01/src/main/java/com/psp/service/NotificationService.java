package com.psp.service;

import com.psp.entity.TxPendingNotification;

import java.util.List;

public interface NotificationService {

    void createNewPendingMessage(TxPendingNotification TxPendingNotification);

    List<TxPendingNotification> pollAllPendingMessage();

    void cancelMessage(String serialNo);
}
