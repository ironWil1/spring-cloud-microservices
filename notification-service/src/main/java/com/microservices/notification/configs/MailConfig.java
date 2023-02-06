package com.microservices.notification.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail-props.properties")
public class MailConfig {
//
//    @Autowired
//    private Environment environment;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("");
        mailSender.setPassword("");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.properties.mail.smtp.ssl.enable", "true");


        return mailSender;
//
//
//
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(environment.getProperty("mail.host"));
//        mailSender.setPort(587);
//        mailSender.setPassword(environment.getProperty("mail.password"));
//        mailSender.setUsername(environment.getProperty("mail.username"));
//
//
//        Properties properties = mailSender.getJavaMailProperties();
//        properties.put("mail.protocol",
//                environment.getProperty("mail.protocol"));
//        properties.put("mail.properties.mail.smtp.auth",
//                environment.getProperty("mail.properties.mail.smtp.auth"));
//        properties.put("mail.properties.mail.smtp.starttls.enable",
//                environment.getProperty("mail.properties.mail.smtp.starttls.enable"));
//        properties.put("mail.smtp.starttls.enable",
//                environment.getProperty("mail.smtp.starttls.enable"));
//        properties.put("mail.properties.mail.smtp.starttls.required",
//                environment.getProperty("mail.properties.mail.smtp.starttls.required"));
//        properties.put("mail.properties.mail.smtp.ssl.enable",
//                environment.getProperty("mail.properties.mail.smtp.ssl.enable"));
//        mailSender.setJavaMailProperties(properties);
//        return mailSender;
    }
}
