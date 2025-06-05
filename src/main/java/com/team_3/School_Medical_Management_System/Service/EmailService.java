package com.team_3.School_Medical_Management_System.Service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendOTP(String toEmail, String otp) {
        // Email functionality is disabled
        System.out.println("Email would be sent to: " + toEmail);
        System.out.println("OTP: " + otp);
    }
}
