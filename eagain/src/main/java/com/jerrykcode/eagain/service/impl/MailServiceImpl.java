package com.jerrykcode.eagain.service.impl;

import com.jerrykcode.eagain.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public boolean sendMail(String receiver, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        try {
            mailMessage.setFrom(String.valueOf(new InternetAddress(sender, "EAGAIN", "UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mailMessage.setTo(receiver);
        mailMessage.setSentDate(new Date());
        try {
            mailSender.send(mailMessage);
            return true;
        }
        catch (MailException e) {
            e.printStackTrace();
        }
        return false;
    }
}
