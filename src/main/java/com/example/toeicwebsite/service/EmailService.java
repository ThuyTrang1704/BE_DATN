package com.example.toeicwebsite.service;


import javax.mail.MessagingException;

public interface EmailService {
        String sendMail(String to, String subject, String text) throws MessagingException;
}
