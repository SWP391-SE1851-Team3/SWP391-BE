package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.Model.Parent;
import com.team_3.School_Medical_Management_System.Repositories.ParentRepo;
import com.team_3.School_Medical_Management_System.Service.EmailService;
import com.team_3.School_Medical_Management_System.Service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class ForgotPasswordController {
    private ParentRepo parentRepo;

    @Autowired
    public ForgotPasswordController(ParentRepo parentRepo) {
        this.parentRepo = parentRepo;
    }

    private Map<String, String> otpStore = new HashMap<>();

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        var p = parentRepo.getParentByEmail(email);
        if (p != null) {
            String otp = String.valueOf(new Random().nextInt(900000) + 100000);
            // Commented out email sending functionality
            // emailService.sendOTP(email, otp);
            // Lưu OTP tạm
            otpStore.put(email, otp);
            System.out.println("OTP for " + email + ": " + otp);
        }
        return ResponseEntity.ok("Mã xác nhận đã được gửi tới email của bạn (Disabled in dev mode).");
    }
}
