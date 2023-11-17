package com.psp.service.impl;

import com.psp.service.ReceivedNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceivedNotificationServiceImpl implements ReceivedNotificationService {

    @Autowired
    private TfReceivedNotificationMapper tfReceivedNotificationMapper;

    @Override
    public void writeNotificationRecord(TfReceivedNotification tfReceivedNotification) {
        //waiting to be sent
        tfReceivedNotification.setNotiStatus("0");
        tfReceivedNotificationMapper.insert(tfReceivedNotification);
    }

    @Override
    public List<TfReceivedNotification> getAllPendingNotifications() {
        return null;
    }

    @Override
    public void cancelNotification(String serialNo) {

    }

    @Override
    public void removePendingMessage(String serialNo) {

    }
}
