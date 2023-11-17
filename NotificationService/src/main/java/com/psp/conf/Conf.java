package com.psp.conf;

import com.psp.message.MailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Conf {

    @Bean
    public MailSender mailSender(@Value("") String host,
                                 @Value("") String userName,
                                 @Value("") String password){

        return new MailSender(host, userName, password);
    }


}
