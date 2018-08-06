package com.gbai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: emailtest
 * @description:
 * @author: gbai
 * @create: 2018-07-09 09:10
 **/
@Component
public class EmailConfig {

    @Value("${spring.mail.username}")
    private  String emailForm;

    public String getEmailForm() {

        return emailForm;
    }

    public void setEmailForm(String emailForm) {
        this.emailForm = emailForm;
    }
}
