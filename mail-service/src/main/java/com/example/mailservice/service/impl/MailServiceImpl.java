package com.example.mailservice.service.impl;

import com.example.mailservice.payload.OrderReq;
import com.example.mailservice.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.from}")
    private String mailFrom;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    SpringTemplateEngine templateEngine;

    @Override
    public void sendEmail(String recipient, String subject, OrderReq req) throws MailException {
        Context context = new Context();
        context.setVariable("idOrder", req.getIdOrder());
        context.setVariable("productName", req.getProductName());
        context.setVariable("qty", req.getQty());
        context.setVariable("total", req.getTotal());

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(mailFrom);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            messageHelper.setText(templateEngine.process("orderCreatedTemplate.html", context), true);
        };
        mailSender.send(messagePreparator);
    }
}
