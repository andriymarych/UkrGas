package com.gas.app.service.email;

public interface MailService {
    void sendEmail(String toEmailAddress, String subject, String templatePath);
}
