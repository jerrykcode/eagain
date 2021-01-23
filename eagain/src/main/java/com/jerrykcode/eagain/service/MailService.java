package com.jerrykcode.eagain.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    boolean sendMail(String receiver, String subject, String message);
}
