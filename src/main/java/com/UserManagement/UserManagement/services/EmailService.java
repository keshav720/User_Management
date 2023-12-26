package com.UserManagement.UserManagement.services;

import com.UserManagement.UserManagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(User user) {
        String subject = "Your New Password";
        String message = "Hello " + user.getUserName() + ",\n\nYour new password is: " + user.getPassword();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("keshav9521797178@gmail.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);

        System.out.println("Email sent successfully to: " + user.getEmail());
    }
}
