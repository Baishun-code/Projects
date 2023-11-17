package com.psp.service.impl;

import com.psp.entity.TfReceivedNotification;
import com.psp.mapper.TfReceivedNotificationMapper;
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
        return tfReceivedNotificationMapper.queryAllPendingMessage();
    }

    @Override
    public void cancelNotification(Integer serialNo) {
        tfReceivedNotificationMapper.cancelPendingMessage(serialNo);
    }

    @Override
    public void removePendingMessage(String serialNo) {

    }
}
