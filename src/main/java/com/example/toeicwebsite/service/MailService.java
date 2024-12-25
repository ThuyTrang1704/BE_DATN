package com.example.toeicwebsite.service;

import java.util.Map;

public interface MailService {
    void sendEmail(String to, String subject, String body);
    void sendEmailWithTemplate(String to, String subject, String templateName, Map<String, Object> templateModel); // Gửi email với template
}
