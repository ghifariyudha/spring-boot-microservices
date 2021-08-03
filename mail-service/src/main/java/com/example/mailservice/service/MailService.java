package com.example.mailservice.service;

import com.example.mailservice.payload.OrderReq;
import org.springframework.mail.MailException;

public interface MailService {
    void sendEmail(String recipient, String subject, OrderReq req) throws MailException;
}
