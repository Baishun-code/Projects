package com.psp.service;

import com.psp.entity.TfReceivedNotification;

public interface MessageSendingHandler {

    void handle(TfReceivedNotification tfReceivedNotification);
}
