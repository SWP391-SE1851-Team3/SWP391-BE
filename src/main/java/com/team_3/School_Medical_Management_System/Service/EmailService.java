package com.team_3.School_Medical_Management_System.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendOTP(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("thanhdinhphot@gmail.com ");
        message.setTo(toEmail);
        message.setSubject("Mã xác nhận khôi phục mật khẩu");
        message.setText("Mã xác nhận của bạn là: " + otp);
        mailSender.send(message);
    }


}
