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
public class EmailVaccinesService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private NotificationsParentRepository notificationsParentRepository;

    @Async("emailTaskExecutor")
    public void sendSimpleNotificationEmail(Parent parent, String title, String content, Integer notificationId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(parent.getEmail());
        message.setSubject(title);

        // Thêm thông tin người gửi và thời gian vào nội dung
        String currentUser = getCurrentUsername();
        String currentDateTime = getCurrentVietnamDateTime();
        String enhancedContent = content + "\n\n" +
                "Thời gian gửi: " + currentDateTime + "\n" +
                "Người gửi: " + currentUser;

        message.setText(enhancedContent);
        mailSender.send(message);

        // Cập nhật trạng thái thông báo thành true sau khi gửi thành công
        updateNotificationStatus(notificationId);
    }

    @Async("emailTaskExecutor")
    public void sendHtmlNotificationEmail(Parent parent, String title, String content, Integer notificationId) {
        try {
            // Lấy thông tin người dùng hiện tại và thời gian
            String currentUser = getCurrentUsername();
            String currentDateTime = getCurrentVietnamDateTime();

            // Tạo MimeMessage với nội dung HTML
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(parent.getEmail());
            helper.setSubject(title);
            helper.setText(createHtmlContent(parent, content, currentDateTime, currentUser), true);

            mailSender.send(message);

            // Cập nhật trạng thái thông báo
            updateNotificationStatus(notificationId);

        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email thông báo: " + e.getMessage(), e);
        }
    }

    /**
     * Lấy tên người dùng hiện tại từ context bảo mật
     */
    private String getCurrentUsername() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = "TRƯỜNG FPT";
        return name;
    }

    /**
     * Lấy thời gian hiện tại theo định dạng và múi giờ Việt Nam
     */
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
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    background-color: #f8f9fa;
                    margin: 0;
                    padding: 0;
                    color: #212529;
                }
                .card {
                    max-width: 600px;
                    margin: 20px auto;
                    background-color: #ffffff;
                    border-radius: 8px;
                    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                    overflow: hidden;
                }
                .header {
                    background-color: #28a745;
                    padding: 20px;
                    text-align: center;
                    color: white;
                    font-size: 20px;
                    font-weight: bold;
                }
                .subtitle {
                    background-color: #218838;
                    color: #e9ecef;
                    padding: 12px 20px;
                    font-weight: 600;
                    font-size: 15px;
                }
                .content {
                    padding: 20px;
                    line-height: 1.6;
                }
                .section {
                    margin-bottom: 16px;
                    padding: 15px;
                    background-color: #e9f7ef;
                    border-left: 4px solid #28a745;
                    border-radius: 4px;
                }
                .section-title {
                    font-weight: 600;
                    color: #28a745;
                    margin-bottom: 8px;
                }
                .footer {
                    background-color: #f1f3f5;
                    padding: 15px;
                    font-size: 13px;
                    text-align: center;
                    color: #6c757d;
                }
                .footer hr {
                    margin: 10px 0;
                    border: none;
                    border-top: 1px solid #dee2e6;
                }
                .highlight {
                    color: #28a745;
                    font-weight: 600;
                }
            </style>
        </head>
        <body>
            <div class="card">
                <div class="header">📋 Cập nhật hồ sơ tiêm chủng học sinh</div>
                <div class="subtitle">
                    Kính chào Quý Phụ huynh <span class="highlight">%s</span>
                </div>
                <div class="content">
                    <div class="section">
                        <div class="section-title">✔ Chi tiết hồ sơ tiêm chủng</div>
                        %s
                    </div>
                    <div class="section">
                        <div class="section-title">📨 Thông tin bổ sung</div>
                        <p>⏰ Thời gian gửi: <strong>%s</strong></p>
                        <p>👤 Người gửi: <strong>%s</strong></p>
                    </div>
                </div>
                <div class="footer">
                    <hr>
                    Trân trọng,<br>
                    <strong>Ban Y tế trường học</strong>
                </div>
            </div>
        </body>
        </html>
    """, parent.getFullName(), formatContentToHtml(content), datetime, username);
    }
    /**
     * Chuyển đổi nội dung văn bản thành HTML
     */
    private String formatContentToHtml(String content) {
        return "<p>" + content.replace("\n\n", "</p><p>")
                .replace("\n", "<br>") + "</p>";
    }

    /**
     * Cập nhật trạng thái thông báo
     */
    private void updateNotificationStatus(Integer notificationId) {
        NotificationsParent notification = notificationsParentRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + notificationId));
        notification.setStatus(true);
        notificationsParentRepository.save(notification);
    }

    /**
     * Phương thức kiểm tra cấu hình email
     */
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
