package com.microservices.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final Environment environment;

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(Environment environment, JavaMailSender javaMailSender) {
        this.environment = environment;
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String mailAddress, String title, String mailMessage) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

    }
}
