package com.psp.message;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSender {

    private Session session;
    private Transport transport;
    private InternetAddress from;

    public MailSender(String host, String userName, String password) {

        Properties properties = System.getProperties();
        properties.setProperty("mail.host", host);
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");

        //create smtp session with authentication information
        session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        try {
            //create smtp transport
            transport = session.getTransport();
            from = new InternetAddress(userName);
            // connect the user which is used to send message
            transport.connect(host, userName, password);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    public void sendMessage(String[] to, String subject, String text){
        try {
            InternetAddress[] addresses = new InternetAddress[to.length];

            for (int i = 0; i < to.length; i++) {
                addresses[i] = new InternetAddress(to[i]);
            }

            //create message
            MimeMessage message = new MimeMessage(session);
            //set source end point
            message.setFrom(from);
            //set target end point
            message.addRecipients(Message.RecipientType.TO, addresses);
            //set subject of the mail
            message.setSubject(subject);
            //set text of the mail
            message.setText(text);
            //send the mail
            transport.send(message);

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
