package com.example.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class EmailService{

    private JavaMailSender javaMailSender;

    public void sendVerificationOtpEmail(String email, String otp) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

        String subject = "Verify OTP";
        String text = "Your Verification code is "+ otp;

        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text);
        mimeMessageHelper.setTo(email);

        try{
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
