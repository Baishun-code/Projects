package com.psp.task;

import com.psp.entity.EmailMessage;
import com.psp.entity.NotificationMessage;
import com.psp.entity.TdUserContactInfo;
import com.psp.entity.TfReceivedNotification;
import org.springframework.stereotype.Component;

@Component
public class EmailMessageFactory implements MessageFactory{

    @Override
    public NotificationMessage buildMessage(TfReceivedNotification tfReceivedNotification,
                                            TdUserContactInfo userInfo) {
        String subject = "";
        String text = "";
        if("0".equals(tfReceivedNotification.getNotiType())){
            subject = "Transfer Finished";
            text = "Hello dear Mr.".concat(userInfo.getUserName()).concat("/n");
            text = text.concat(" You have successfully transferred ")
                    .concat(tfReceivedNotification.getContent())
                    .concat(" to the target account.");
        }
        EmailMessage emailMessage = new EmailMessage(subject, text);
        return emailMessage;
    }
}
