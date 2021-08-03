package com.example.mailservice.consumer;

import com.example.mailservice.payload.OrderReq;
import com.example.mailservice.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;

@Slf4j
public class ConsumerOrder {

    @Autowired
    MailService mailService;

    @KafkaListener(topicPattern = "${kafka.topics.orderCreated}", autoStartup = "${kafka.enabled}")
    public void receive(OrderReq req) {
        log.info("Receive from listener {}", req.toString());
        try {
            mailService.sendEmail(
                        req.getEmailUser(),
                        "Your order has been successfully placed!",
                        req);
        } catch (MailException e) {
            log.error("Could not send e-mail", e.getMessage());
        }
    }
}
