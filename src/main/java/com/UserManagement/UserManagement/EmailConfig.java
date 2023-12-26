package com.UserManagement.UserManagement;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig
{
    @Bean
    public JavaMailSender getJavaMailSender()
    {
        JavaMailSender mailSender = new JavaMailSenderImpl();
        ((JavaMailSenderImpl) mailSender).setHost("smtp.gmail.com");
        ((JavaMailSenderImpl) mailSender).setPort(25);

        ((JavaMailSenderImpl) mailSender).setUsername("keshav9521797178@gmail.com");
        ((JavaMailSenderImpl) mailSender).setPassword("ywnzsfkiytojealm");

        Properties props = ((JavaMailSenderImpl) mailSender).getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public SimpleMailMessage emailTemplate()
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("somebody@gmail.com");
        message.setFrom("admin@gmail.com");
        message.setText("FATAL - Application crash. Save your job !!");
        return message;
    }
}