package com.team_3.School_Medical_Management_System.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


@Configuration
public class EmailConfig {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();


        // Cấu hình server email
        mailSender.setHost("smtp.gmail.com"); // Thay đổi nếu bạn sử dụng nhà cung cấp email khác
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setDefaultEncoding("UTF-8"); // Hỗ trợ tiếng Việt

        // Cấu hình thuộc tính bổ sung
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // Đặt thành false trong môi trường production
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // Tin tưởng chứng chỉ SSL của Gmail

        return mailSender;
    }
}


