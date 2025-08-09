package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.InterfaceRepo.NotificationsParentRepository;
import com.team_3.School_Medical_Management_System.Model.NotificationsParent;
import com.team_3.School_Medical_Management_System.Model.Parent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class EmailSentConsentForm {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NotificationsParentRepository notificationsParentRepository;

    @Async("emailTaskExecutor")
    public void sendSimpleNotificationEmail(Parent parent, String title, String content, Integer notificationId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(parent.getEmail());
        message.setSubject(title);

        String currentUser = getCurrentUsername();
        String currentDateTime = getCurrentVietnamDateTime();
        String enhancedContent = content + "\n\n" +
                "Thời gian gửi: " + currentDateTime + "\n" +
                "Người gửi: " + currentUser;

        message.setText(enhancedContent);
        mailSender.send(message);

        updateNotificationStatus(notificationId);
    }

    @Async("emailTaskExecutor")
    public void sendHtmlNotificationEmail(Parent parent, String title, String content, Integer notificationId) {
        try {
            String currentUser = getCurrentUsername();
            String currentDateTime = getCurrentVietnamDateTime();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(parent.getEmail());
            helper.setSubject(title);
            helper.setText(createHtmlContent(parent, content, currentDateTime, currentUser), true);

            mailSender.send(message);
            updateNotificationStatus(notificationId);
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email thông báo: " + e.getMessage(), e);
        }
    }

    private String getCurrentUsername() {
        return "TRƯỜNG FPT";
    }

    private String getCurrentVietnamDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")).format(formatter);
    }

    private String createHtmlContent(Parent parent, String content, String datetime, String username) {
        return String.format("""
        <html>
        <head>
            <meta charset="UTF-8">
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #0f0f0f;
                    margin: 0;
                    padding: 0;
                }
                .card {
                    background-color: #000;
                    max-width: 600px;
                    margin: 20px auto;
                    border-radius: 12px;
                    overflow: hidden;
                    box-shadow: 0 4px 12px rgba(0,0,0,0.5);
                }
                .header {
                    background-color: #059669;
                    padding: 20px;
                    text-align: center;
                    border-top-left-radius: 12px;
                    border-top-right-radius: 12px;
                }
                .header h2 {
                    margin: 0;
                    color: #fff;
                    font-size: 22px;
                }
                .header p {
                    margin: 4px 0 0 0;
                    color: #d1fae5;
                    font-size: 14px;
                }
                .subtitle {
                    background-color: #065f46;
                    padding: 12px 20px;
                    color: #fff;
                    font-weight: 600;
                    font-size: 15px;
                }
                .content {
                    padding: 20px;
                    background-color: #000;
                    color: #e5e7eb;
                    line-height: 1.6;
                }
                .content p {
                    margin: 0 0 10px 0;
                }
                .section {
                    background-color: #064e3b;
                    margin-bottom: 16px;
                    padding: 15px;
                    border-radius: 8px;
                    color: #fff;
                }
                .section-title {
                    font-weight: 600;
                    color: #34d399;
                    margin-bottom: 8px;
                    font-size: 14px;
                }
                .icon {
                    margin-right: 6px;
                }
                .highlight {
                    color: #34d399;
                    font-weight: 600;
                }
            </style>
        </head>
        <body>
            <div class="card">
                <div class="header">
                    <h2>💉 Thông Báo Tiêm Chủng</h2>
                    <p>Hệ thống quản lý y tế trường học</p>
                </div>
                <div class="subtitle">
                    Kính chào Quý Phụ huynh <span class="highlight">%s</span>!
                </div>
                <div class="content">
                    <p>Chúng tôi xin thông báo lịch <span class="highlight">tiêm chủng</span> cho con của Quý Phụ huynh.</p>
                    <div class="section">
                        <div class="section-title">✔ Chi Tiết Tiêm Chủng</div>
                        %s
                    </div>
                    <div class="section">
                        <div class="section-title">📩 Thông Tin Gửi Thông Báo</div>
                        <p>⏰ Thời gian gửi: <strong>%s</strong></p>
                        <p>👤 Người gửi: <strong>%s</strong></p>
                    </div>
                </div>
            </div>
        </body>
        </html>
    """, parent.getFullName(), formatContentToHtml(content), datetime, username);
    }


    private String formatContentToHtml(String content) {
        return "<p>" + content.replace("\n\n", "</p><p>")
                .replace("\n", "<br>") + "</p>";
    }

    private void updateNotificationStatus(Integer notificationId) {
        NotificationsParent notification = notificationsParentRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + notificationId));
        notification.setStatus(true);
        notificationsParentRepository.save(notification);
    }

    public void testEmailConfig(String to) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Kiểm tra cấu hình email");
            helper.setText(
                    "<html><body>" +
                            "<h2>Kiểm tra email</h2>" +
                            "<p>Đây là email kiểm tra cấu hình.</p>" +
                            "<p>Thời gian: " + getCurrentVietnamDateTime() + "</p>" +
                            "<p>Người gửi: " + getCurrentUsername() + "</p>" +
                            "</body></html>",
                    true
            );

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email kiểm tra: " + e.getMessage(), e);
        }
    }
}
