package com.gas.app.service.email.impl;

import com.gas.app.service.email.MailService;
import com.gas.app.service.email.ThymeLeafService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailSenderService implements MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmailAddress;

    private final ThymeLeafService thymeLeafService;

    @Override
    public void sendEmail(String toEmailAddress, String subject, String templatePath) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            messageHelper.setFrom(fromEmailAddress, "UKR GAS");
            messageHelper.setTo(toEmailAddress);
            message.setSubject(subject);
            messageHelper.setText(thymeLeafService.createContent("meter-reading-email-template.html", null),
                    true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
