package com.example.toeicwebsite.controller;

import com.example.toeicwebsite.service.EmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/mail")
public class EmailSendController {
    @Autowired
    private EmailService emailService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Student')")
    @PostMapping("/send")
    public String sendMail(@RequestParam String to, String subject, String text) throws MessagingException {
//        String to = (String) emailSender.get("to");
//        String subject = (String) emailSender.get("subject");
//        String text = (String) emailSender.get("text");
        return emailService.sendMail(to, subject, text);
    }

}
