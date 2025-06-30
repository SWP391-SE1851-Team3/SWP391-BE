package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.InterfaceRepo.NotificationsParentRepository;
import com.team_3.School_Medical_Management_System.Model.NotificationsParent;
import com.team_3.School_Medical_Management_System.Model.Parent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NotificationsParentRepository notificationsParentRepository;
    public void sendOTP(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("thanhdinhphot@gmail.com ");
        message.setTo(toEmail);
        message.setSubject("Mã xác nhận khôi phục mật khẩu");
        message.setText("Mã xác nhận của bạn là: " + otp);
        mailSender.send(message);
    }

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

    public void sendHtmlNotificationEmail(Parent parent, String title, String content, Integer notificationId,String nameNurse) {
        try {
            // Lấy thông tin người dùng hiện tại và thời gian
            String currentUser = getCurrentUsername();
            String currentDateTime = getCurrentVietnamDateTime();

            // Tạo MimeMessage với nội dung HTML
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(parent.getEmail());
            helper.setSubject(title);
            helper.setText(createHtmlContent(parent, content, currentDateTime, currentUser,nameNurse), true);

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
        String name = "NHÂN VIÊN Y TẾ TRƯỜNG FP";
        return  name;
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
    private String createHtmlContent(Parent parent, String content, String datetime, String username, String currentUser) {
        return String.format("""
        <!DOCTYPE html>
        <html lang="vi">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Thông báo y tế khẩn cấp</title>
            <style>
                * {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                }
                
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    line-height: 1.6;
                    color: #333;
                    background-color: #f4f4f4;
                }
                
                .email-container {
                    max-width: 600px;
                    margin: 20px auto;
                    background-color: #ffffff;
                    border-radius: 10px;
                    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                    overflow: hidden;
                }
                
                .header {
                    background: linear-gradient(135deg, #e74c3c, #c0392b);
                    color: white;
                    padding: 30px 20px;
                    text-align: center;
                    position: relative;
                }
                
                .header::before {
                    content: '🚨';
                    font-size: 3em;
                    display: block;
                    margin-bottom: 10px;
                }
                
                .header h1 {
                    font-size: 24px;
                    font-weight: 600;
                    margin-bottom: 5px;
                }
                
                .header .subtitle {
                    font-size: 14px;
                    opacity: 0.9;
                }
                
                .content {
                    padding: 30px 25px;
                }
                
                .greeting {
                    font-size: 18px;
                    font-weight: 600;
                    color: #2c3e50;
                    margin-bottom: 20px;
                }
                
                .alert-box {
                    background-color: #fff5f5;
                    border-left: 4px solid #e74c3c;
                    padding: 20px;
                    margin: 20px 0;
                    border-radius: 5px;
                }
                
                .alert-text {
                    font-size: 16px;
                    line-height: 1.7;
                    color: #2c3e50;
                    margin-bottom: 15px;
                }
                
                .info-grid {
                    display: grid;
                    grid-template-columns: 1fr 1fr;
                    gap: 15px;
                    margin: 25px 0;
                }
                
                .info-item {
                    background-color: #f8f9fa;
                    padding: 15px;
                    border-radius: 8px;
                    border-left: 3px solid #3498db;
                }
                
                .info-item .label {
                    font-weight: 600;
                    color: #34495e;
                    font-size: 14px;
                    margin-bottom: 5px;
                }
                
                .info-item .value {
                    color: #2c3e50;
                    font-size: 16px;
                }
                
                .contact-section {
                    background: linear-gradient(135deg, #3498db, #2980b9);
                    color: white;
                    padding: 25px;
                    margin: 25px 0;
                    border-radius: 10px;
                    text-align: center;
                }
                
                .contact-section h3 {
                    margin-bottom: 15px;
                    font-size: 20px;
                }
                
                .phone-number {
                    background-color: rgba(255, 255, 255, 0.2);
                    padding: 15px 25px;
                    border-radius: 50px;
                    font-size: 24px;
                    font-weight: bold;
                    display: inline-block;
                    margin: 10px 0;
                    letter-spacing: 2px;
                }
                
                .actions {
                    text-align: center;
                    margin: 25px 0;
                }
                
                .btn {
                    display: inline-block;
                    padding: 12px 30px;
                    background: linear-gradient(135deg, #27ae60, #229954);
                    color: white;
                    text-decoration: none;
                    border-radius: 25px;
                    font-weight: 600;
                    margin: 0 10px;
                    transition: transform 0.2s;
                }
                
                .btn:hover {
                    transform: translateY(-2px);
                }
                
                .footer {
                    background-color: #2c3e50;
                    color: #bdc3c7;
                    padding: 25px;
                    text-align: center;
                    font-size: 14px;
                }
                
                .footer .school-info {
                    margin-bottom: 15px;
                }
                
                .footer .timestamp {
                    font-size: 12px;
                    opacity: 0.8;
                    border-top: 1px solid #34495e;
                    padding-top: 15px;
                    margin-top: 15px;
                }
                
                @media (max-width: 600px) {
                    .email-container {
                        margin: 10px;
                        border-radius: 0;
                    }
                    
                    .info-grid {
                        grid-template-columns: 1fr;
                    }
                    
                    .content {
                        padding: 20px 15px;
                    }
                    
                    .phone-number {
                        font-size: 20px;
                        padding: 12px 20px;
                    }
                }
            </style>
        </head>
        <body>
            <div class="email-container">
                <div class="header">
                    <h1>THÔNG BÁO Y TẾ KHẨN CẤP</h1>
                    <div class="subtitle">Từ Ban Giám Hiệu Trường</div>
                </div>
                
                <div class="content">
                    <div class="greeting">
                        Kính gửi phụ huynh %s,
                    </div>
                    
                    <div class="alert-box">
                        <div class="alert-text">
                            Chúng tôi xin thông báo có <strong>sự kiện y tế khẩn cấp</strong> 
                            xảy ra tại trường học. Chúng tôi đã xử lý tình huống một cách 
                            chuyên nghiệp và đảm bảo an toàn cho học sinh.
                        </div>
                    </div>
                    
                    <div class="info-grid">
                      
                        <div class="info-item">
                            <div class="label">👨‍⚕️ Người xử lý</div>
                            <div class="value">%s</div>
                        </div>
                    </div>
                    
                    <div class="alert-text">
                        <strong>Thông tin chi tiết:</strong><br>
                        %s
                    </div>
                    
                    <div class="contact-section">
                        <h3>📞 LIÊN HỆ NGAY</h3>
                        <p>Để biết thêm thông tin chi tiết, vui lòng liên hệ:</p>
                        <div class="phone-number">19001818</div>
                        <p><small>Đường dây nóng - Hoạt động 24/7</small></p>
                    </div>
                    
                    <div class="actions">
                        <a href="tel:19001818" class="btn">📞 Gọi ngay</a>
                        <a href="#" class="btn">📋 Xem chi tiết</a>
                    </div>
                </div>
                
                <div class="footer">
                    <div class="school-info">
                        <strong>Ban Giám Hiệu Trường</strong><br>
                        Email: info@school.edu.vn | Website: www.school.edu.vn
                    </div>
                    <div class="timestamp">
                        Email được gửi lúc: %s<br>
                        Người gửi: %s
                    </div>
                </div>
            </div>
        </body>
        </html>
        """,
                parent.getFullName(),
               // savedEvent.getEventDateTime(), // Thời gian sự kiện
                currentUser, // Người xử lý
                content, // Nội dung chi tiết
                datetime, // Thời gian gửi email
                currentUser // Người gửi
        );
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


    private String createHtmlContent_HealthRecord(Parent parent, String content, String datetime, String username) {
        return String.format(
                "<html>" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <style>" +
                        "        body { font-family: Arial, sans-serif; }" +
                        "        .header { background-color: #28a745; color: white; padding: 10px; text-align: center; }" +
                        "        .content { margin: 20px; line-height: 1.6; }" +
                        "        .footer { background-color: #f8f9fa; padding: 10px; font-size: smaller; }" +
                        "        .info { color: #6c757d; }" +
                        "    </style>" +
                        "</head>" +
                        "<body>" +
                        "    <div class=\"header\"><h2>Thông báo hồ sơ tiêm chủng của học sinh</h2></div>" +
                        "    <div class=\"content\">" +
                        "        %s" +
                        "    </div>" +
                        "    <div class=\"footer\">" +
                        "        <p class=\"info\">Thông tin bổ sung:</p>" +
                        "        <p>Thời gian gửi: %s</p>" +
                        "        <p>Người gửi: %s</p>" +
                        "        <hr>" +
                        "        <p>Trân trọng,<br>Ban y tế trường học</p>" +
                        "    </div>" +
                        "</body>" +
                        "</html>",
                formatContentToHtml(content),
                datetime,
                username
        );
    }

}
