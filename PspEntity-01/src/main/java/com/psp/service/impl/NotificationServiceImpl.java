package com.psp.service.impl;

import com.psp.entity.TxPendingNotification;
import com.psp.mapper.TxPendingNotificationMapper;
import com.psp.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private TxPendingNotificationMapper txPendingNotificationMapper;

    @Override
    public void createNewPendingMessage(TxPendingNotification txPendingNotification) {
        txPendingNotificationMapper.insert(txPendingNotification);
    }

    @Override
    public List<TxPendingNotification> pollAllPendingMessage() {
        return txPendingNotificationMapper.queryAllPendingMessage();
    }

    @Override
    public void cancelMessage(Integer serialNo) {
        txPendingNotificationMapper.deleteFinishedMessage(serialNo);
    }
}
