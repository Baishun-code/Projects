package com.psp.task;

import com.psp.entity.NotificationMessage;
import com.psp.entity.TdUserContactInfo;
import com.psp.entity.TfReceivedNotification;

public interface MessageFactory {

    NotificationMessage buildMessage(TfReceivedNotification tfReceivedNotification,
                                     TdUserContactInfo userInfo);
}
