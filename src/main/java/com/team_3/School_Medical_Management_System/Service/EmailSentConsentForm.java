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

    /**
     * Tạo nội dung HTML đẹp mắt cho email
     */
    private String createHtmlContent(Parent parent, String content, String datetime, String username) {
        return String.format("""
            <html>
            <head>
                <meta charset=\"UTF-8\">
                <style>
                    body { font-family: Arial, sans-serif; background-color: #0f0f0f; margin: 0; padding: 0; }
                    .card { background-color: #1c1c1e; color: #fff; max-width: 600px; margin: auto; border-radius: 10px; overflow: hidden; box-shadow: 0 4px 10px rgba(0,0,0,0.5); }
                    .header { background: linear-gradient(90deg, #0ea5e9, #2563eb); padding: 20px; text-align: center; }
                    .header h2 { margin: 0; color: white; }
                    .subtitle { background-color: #111827; padding: 10px 20px; color: #facc15; font-weight: bold; }
                    .content { padding: 20px; line-height: 1.6; background-color: #1f2937; }
                    .section { background-color: #111827; margin: 15px 20px; padding: 15px; border-left: 4px solid #10b981; border-radius: 5px; }
                    .section-title { font-weight: bold; color: #10b981; margin-bottom: 5px; }
                    .footer { text-align: center; background-color: #1e3a8a; padding: 15px; color: #cbd5e1; font-size: 13px; }
                    .footer p { margin: 5px 0; }
                    .highlight { color: #facc15; font-weight: bold; }
                </style>
            </head>
            <body>
                <div class=\"card\">
                    <div class=\"header\">
                        <h2>🧾 Thông Báo Khám Sức Khỏe</h2>
                        <p style=\"margin-top: 5px; font-size: 14px;\">Hệ thống quản lý y tế trường học</p>
                    </div>
                    <div class=\"subtitle\">
                        Kính chào Quý Phụ huynh <span class=\"highlight\">%s</span>!
                    </div>
                    <div class=\"content\">
                        <p>Chúng tôi gửi đến Quý Phụ huynh thông báo quan trọng về <span class=\"highlight\">lịch khám sức khỏe</span> của con em.</p>
                        <div class=\"section\">
                            <div class=\"section-title\">✔ Chi Tiết Thông Báo</div>
                            %s
                        </div>
                        <div class=\"section\">
                            <div class=\"section-title\">📩 Thông Tin Gửi Thông Báo</div>
                            <p>⏰ Thời gian gửi: <strong>%s</strong></p>
                            <p>👤 Người gửi: <strong>%s</strong></p>
                        </div>
                    </div>
                    <div class=\"footer\">
                        <p>📘 Ban Y Tế Trường Học</p>
                        <p>Chúng tôi cam kết chăm sóc sức khỏe tốt nhất cho các học sinh.</p>
                        <p><em>Hệ thống quản lý y tế hiện đại và chuyên nghiệp</em></p>
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
