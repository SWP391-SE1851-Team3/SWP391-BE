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

    public void sendHtmlNotificationEmailMedicalEvent(Parent parent, String title, String content, Integer notificationId,String nameNurse) {
        try {
            // Lấy thông tin người dùng hiện tại và thời gian
            String currentUser = getCurrentUsername();
            String currentDateTime = getCurrentVietnamDateTime();

            // Tạo MimeMessage với nội dung HTML
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(parent.getEmail());
            helper.setSubject(title);
            helper.setText(createHtmlContentMedicalEvent(parent, content, currentDateTime, currentUser,nameNurse), true);

            mailSender.send(message);

            // Cập nhật trạng thái thông báo
            updateNotificationStatus(notificationId);

        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email thông báo: " + e.getMessage(), e);
        }
    }

    public void sendHtmlNotificationEmailForHealthCheck(Parent parent, String title, String content, Integer notificationId) {
        try {
            // Lấy thông tin người dùng hiện tại và thời gian
            String currentUser = getCurrentUsername();
            String currentDateTime = getCurrentVietnamDateTime();

            // Tạo MimeMessage với nội dung HTML
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(parent.getEmail());
            helper.setSubject(title);
            helper.setText(createHtmlContentForHealthCheck(parent, content, currentDateTime, currentUser), true);

            mailSender.send(message);

            // Cập nhật trạng thái thông báo
            updateNotificationStatus(notificationId);

        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email thông báo: " + e.getMessage(), e);
        }
    }

    public void sendHtmlNotificationEmailForHealthCheckStudent(Parent parent, String title, String content, Integer notificationId) {
        try {
            // Lấy thông tin người dùng hiện tại và thời gian
            String currentUser = getCurrentUsername();
            String currentDateTime = getCurrentVietnamDateTime();

            // Tạo MimeMessage với nội dung HTML
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(parent.getEmail());
            helper.setSubject(title);
            helper.setText(createHtmlContentForHealthCheckStudent(parent, content, currentDateTime, currentUser), true);

            mailSender.send(message);

            // Cập nhật trạng thái thông báo
            updateNotificationStatus(notificationId);

        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email thông báo: " + e.getMessage(), e);
        }
    }

    public void sendHtmlNotificationEmailForHealthCheckConsultation(Parent parent, String title, String content, Integer notificationId) {
        try {
            // Lấy thông tin người dùng hiện tại và thời gian
            String currentUser = getCurrentUsername();
            String currentDateTime = getCurrentVietnamDateTime();

            // Tạo MimeMessage với nội dung HTML
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(parent.getEmail());
            helper.setSubject(title);
            helper.setText(createHtmlContentForHealthCheckConsultation(parent, content, currentDateTime, currentUser), true);

            mailSender.send(message);

            // Cập nhật trạng thái thông báo
            updateNotificationStatus(notificationId);

        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email thông báo: " + e.getMessage(), e);
        }
    }

    public void sendHtmlNotificationEmailForConfirmMedication(Parent parent, String title, String content, Integer notificationId) {
        try {
            // Lấy thông tin người dùng hiện tại và thời gian
            String currentUser = getCurrentUsername();
            String currentDateTime = getCurrentVietnamDateTime();

            // Tạo MimeMessage với nội dung HTML
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(parent.getEmail());
            helper.setSubject(title);
            helper.setText(createHtmlContentForMedicationConfirmation(parent, content, currentDateTime, currentUser), true);

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

    private String createHtmlContentForMedicationConfirmation(Parent parent, String content, String datetime, String username) {
        return String.format(
                "<!DOCTYPE html>" +
                        "<html lang=\"vi\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                        "    <title>Thông báo cho học sinh uống thuốc</title>" +
                        "    <style>" +
                        "        * { margin: 0; padding: 0; box-sizing: border-box; }" +
                        "        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; padding: 20px; }" +
                        "        .email-container { max-width: 600px; margin: 0 auto; background: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }" +
                        "        .header { background: linear-gradient(135deg, #17a2b8, #138496); color: white; padding: 30px 25px; text-align: center; }" +
                        "        .header h1 { font-size: 24px; margin-bottom: 8px; font-weight: 600; }" +
                        "        .header .icon { font-size: 40px; margin-bottom: 12px; }" +
                        "        .header .subtitle { font-size: 14px; opacity: 0.9; }" +
                        "        .success-badge { background: #28a745; color: white; padding: 6px 12px; border-radius: 20px; font-size: 12px; font-weight: bold; margin-top: 10px; display: inline-block; }" +
                        "        .greeting { background: #f8f9fa; padding: 25px; border-left: 4px solid #17a2b8; }" +
                        "        .greeting h2 { color: #17a2b8; font-size: 20px; margin-bottom: 12px; font-weight: 600; }" +
                        "        .greeting p { color: #495057; line-height: 1.6; font-size: 15px; }" +
                        "        .main-content { padding: 30px 25px; }" +
                        "        .medication-status { background: #d4edda; border: 1px solid #c3e6cb; border-radius: 8px; padding: 25px; margin: 20px 0; text-align: center; }" +
                        "        .medication-status h4 { color: #155724; font-size: 18px; margin-bottom: 15px; display: flex; align-items: center; justify-content: center; }" +
                        "        .medication-status .status-icon { color: #28a745; margin-right: 8px; font-size: 28px; }" +
                        "        .medication-status p { color: #155724; font-size: 16px; font-weight: 500; }" +
                        "        .medication-status .timestamp { color: #6c757d; font-size: 14px; margin-top: 10px; font-style: italic; }" +
                        "        .medication-info { background: #e8f6f3; border: 2px solid #17a2b8; border-radius: 8px; padding: 25px; margin: 20px 0; }" +
                        "        .medication-info h3 { color: #17a2b8; font-size: 18px; margin-bottom: 15px; display: flex; align-items: center; }" +
                        "        .medication-info .icon { color: #17a2b8; margin-right: 10px; font-size: 20px; }" +
                        "        .content-text { color: #495057; line-height: 1.7; font-size: 15px; }" +
                        "        .safety-info { background: #fff3cd; border: 1px solid #ffeaa7; border-radius: 8px; padding: 20px; margin: 20px 0; }" +
                        "        .safety-info h4 { color: #856404; font-size: 16px; margin-bottom: 15px; display: flex; align-items: center; }" +
                        "        .safety-info .safety-icon { color: #ffc107; margin-right: 8px; font-size: 18px; }" +
                        "        .safety-info p { color: #856404; font-size: 14px; line-height: 1.6; margin-bottom: 8px; }" +
                        "        .contact-info { background: #e8f4f8; border-radius: 8px; padding: 20px; margin: 20px 0; text-align: center; }" +
                        "        .contact-info h4 { color: #0c5460; font-size: 16px; margin-bottom: 15px; }" +
                        "        .contact-button { background: #17a2b8; color: white; padding: 12px 24px; border-radius: 6px; text-decoration: none; font-weight: 500; margin: 8px; display: inline-block; transition: all 0.3s ease; }" +
                        "        .contact-button:hover { background: #138496; transform: translateY(-2px); box-shadow: 0 4px 8px rgba(23,162,184,0.3); }" +
                        "        .info-section { background: #f8f9fa; border-radius: 8px; padding: 20px; margin: 20px 0; }" +
                        "        .info-title { color: #495057; font-weight: 600; font-size: 16px; margin-bottom: 15px; }" +
                        "        .info-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; }" +
                        "        .info-item { background: #ffffff; border: 1px solid #dee2e6; padding: 15px; border-radius: 6px; }" +
                        "        .info-label { font-weight: 600; color: #6c757d; font-size: 12px; margin-bottom: 5px; text-transform: uppercase; }" +
                        "        .info-value { color: #495057; font-size: 14px; }" +
                        "        .footer { background: #343a40; color: #ffffff; padding: 25px; text-align: center; }" +
                        "        .footer h3 { font-size: 18px; margin-bottom: 15px; color: #ffffff; }" +
                        "        .footer p { line-height: 1.6; margin-bottom: 8px; font-size: 14px; color: #adb5bd; }" +
                        "        .footer .divider { height: 1px; background: #495057; margin: 15px 0; }" +
                        "        .highlight { background: #bee5eb; color: #0c5460; padding: 3px 8px; border-radius: 4px; font-weight: 500; }" +
                        "        .medication-success { color: #28a745; font-weight: 600; }" +
                        "        .pulse { animation: pulse 2s infinite; }" +
                        "        @keyframes pulse { 0%% { transform: scale(1); } 50%% { transform: scale(1.05); } 100%% { transform: scale(1); } }" +
                        "        @media (max-width: 600px) {" +
                        "            body { padding: 10px; }" +
                        "            .email-container { border-radius: 4px; }" +
                        "            .info-grid { grid-template-columns: 1fr; }" +
                        "            .main-content, .greeting { padding: 20px; }" +
                        "            .header { padding: 25px 20px; }" +
                        "        }" +
                        "    </style>" +
                        "</head>" +
                        "<body>" +
                        "    <div class=\"email-container\">" +
                        "        <div class=\"header\">" +
                        "            <div class=\"icon\">💊</div>" +
                        "            <h1>Thông Báo Cho Học Sinh Uống Thuốc</h1>" +
                        "            <p class=\"subtitle\">Hệ thống quản lý y tế trường học</p>" +
                        "            <span class=\"success-badge pulse\">✅ ĐÃ HOÀN THÀNH</span>" +
                        "        </div>" +
                        "        <div class=\"greeting\">" +
                        "            <h2>Kính chào Quý Phụ huynh %s</h2>" +
                        "            <p>Chúng tôi xin thông báo đến Quý Phụ huynh rằng con em đã được <span class=\"highlight\">cho uống thuốc an toàn</span> theo đúng chỉ định và quy trình y tế của trường.</p>" +
                        "        </div>" +
                        "        <div class=\"main-content\">" +
                        "            <div class=\"medication-status\">" +
                        "                <h4><span class=\"status-icon\">✅</span>Thực Hiện Thành Công</h4>" +
                        "                <p class=\"medication-success\">Con em đã được cho uống thuốc một cách an toàn</p>" +
                        "                <p class=\"timestamp\">Đã xác nhận bởi đội ngũ y tế trường học</p>" +
                        "            </div>" +
                        "            <div class=\"medication-info\">" +
                        "                <h3><span class=\"icon\">📋</span>Chi Tiết Thông Tin Dùng Thuốc</h3>" +
                        "                <div class=\"content-text\">%s</div>" +
                        "            </div>" +
                        "            <div class=\"safety-info\">" +
                        "                <h4><span class=\"safety-icon\">🛡️</span>Cam Kết An Toàn</h4>" +
                        "                <p>• Việc cho học sinh uống thuốc đã được thực hiện theo đúng quy trình nghiêm ngặt</p>" +
                        "                <p>• Y tá đã kiểm tra kỹ lưỡng thông tin học sinh và loại thuốc trước khi thực hiện</p>" +
                        "                <p>• Học sinh đã được theo dõi sát sau khi uống thuốc để đảm bảo không có phản ứng bất thường</p>" +
                        "                <p>• Nếu có bất kỳ thắc mắc nào, Quý Phụ huynh vui lòng liên hệ ngay với ban y tế</p>" +
                        "            </div>" +
                        "            <div class=\"contact-info\">" +
                        "                <h4>📞 Liên Hệ Hỗ Trợ 24/7</h4>" +
                        "                <p style=\"color: #0c5460; margin-bottom: 15px;\">Đội ngũ y tế luôn sẵn sàng hỗ trợ Quý Phụ huynh</p>" +
                        "                <a href=\"tel:+84123456789\" class=\"contact-button\">📞 Gọi điện ngay</a>" +
                        "                <a href=\"mailto:ytetrường@school.edu.vn\" class=\"contact-button\">📧 Gửi email</a>" +
                        "            </div>" +
                        "            <div class=\"info-section\">" +
                        "                <div class=\"info-title\">📊 Thông tin chi tiết</div>" +
                        "                <div class=\"info-grid\">" +
                        "                    <div class=\"info-item\">" +
                        "                        <div class=\"info-label\">⏰ Thời gian thông báo</div>" +
                        "                        <div class=\"info-value\">%s</div>" +
                        "                    </div>" +
                        "                    <div class=\"info-item\">" +
                        "                        <div class=\"info-label\">👨‍⚕️ Người thực hiện</div>" +
                        "                        <div class=\"info-value\">%s</div>" +
                        "                    </div>" +
                        "                    <div class=\"info-item\">" +
                        "                        <div class=\"info-label\">🏥 Đơn vị</div>" +
                        "                        <div class=\"info-value\">Ban Y Tế Trường Học</div>" +
                        "                    </div>" +
                        "                    <div class=\"info-item\">" +
                        "                        <div class=\"info-label\">📋 Trạng thái</div>" +
                        "                        <div class=\"info-value medication-success\">Hoàn thành</div>" +
                        "                    </div>" +
                        "                </div>" +
                        "            </div>" +
                        "        </div>" +
                        "        <div class=\"footer\">" +
                        "            <h3>🏫 Ban Y Tế Trường Học</h3>" +
                        "            <p>Chúng tôi cam kết đảm bảo an toàn và chăm sóc sức khỏe tốt nhất cho các em học sinh.</p>" +
                        "            <div class=\"divider\"></div>" +
                        "            <p>🎯 Sức khỏe của con em là ưu tiên hàng đầu của chúng tôi</p>" +
                        "            <p>📧 Email tự động - Vui lòng không trả lời trực tiếp</p>" +
                        "            <p>🔒 Thông tin này được bảo mật theo quy định</p>" +
                        "        </div>" +
                        "    </div>" +
                        "</body>" +
                        "</html>",
                parent.getFullName() != null ? parent.getFullName() : "Quý Phụ huynh",
                formatContentToHtml(content),
                datetime,
                username
        );
    }
    private String createHtmlContentMedicalEvent(Parent parent, String content, String datetime, String username, String nameNurse) {
        return String.format(
                "<!DOCTYPE html>" +

                        "<html lang=\"vi\">" +
                        "<head>" +
                        "    <meta charset=\"UTF-8\">" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                        "    <title>Thông báo y tế khẩn cấp</title>" +
                        "    <style>" +
                        "        * { margin: 0; padding: 0; box-sizing: border-box; }" +
                        "        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f5f5f5; }" +
                        "        .email-container { max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 20px rgba(0,0,0,0.1); }" +
                        "        .header { background: linear-gradient(135deg, #dc3545, #c82333); color: white; padding: 30px 20px; text-align: center; position: relative; }" +
                        "        .header h1 { font-size: 24px; margin-bottom: 8px; position: relative; z-index: 1; }" +
                        "        .header .icon { font-size: 48px; margin-bottom: 15px; }" +
                        "        .header .subtitle { font-size: 14px; opacity: 0.9; position: relative; z-index: 1; }" +
                        "        .content-wrapper { padding: 0; }" +
                        "        .greeting { background: #f8f9fa; padding: 25px 30px; border-left: 4px solid #dc3545; }" +
                        "        .greeting h2 { color: #333; font-size: 18px; margin-bottom: 10px; }" +
                        "        .greeting p { color: #666; line-height: 1.6; }" +
                        "        .main-content { padding: 30px; }" +
                        "        .content-box { background: #fff5f5; border-radius: 8px; padding: 25px; margin: 20px 0; border-left: 4px solid #dc3545; }" +
                        "        .content-box h3 { color: #dc3545; font-size: 16px; margin-bottom: 15px; }" +
                        "        .content-text { color: #555; line-height: 1.8; font-size: 15px; }" +
                        "        .info-section { background: linear-gradient(135deg, #ffeaa7, #fab1a0); padding: 25px; margin: 25px 0; border-radius: 10px; }" +
                        "        .info-title { color: #d63031; font-weight: bold; font-size: 16px; margin-bottom: 15px; }" +
                        "        .info-grid { display: flex; flex-wrap: wrap; gap: 15px; }" +
                        "        .info-item { background: rgba(255,255,255,0.8); padding: 12px 15px; border-radius: 6px; flex: 1; min-width: 200px; }" +
                        "        .info-label { font-weight: 600; color: #333; font-size: 13px; margin-bottom: 4px; }" +
                        "        .info-value { color: #666; font-size: 14px; }" +
                        "        .footer { background: #2c3e50; color: #ecf0f1; padding: 30px; text-align: center; }" +
                        "        .footer-content { max-width: 400px; margin: 0 auto; }" +
                        "        .footer h3 { font-size: 18px; margin-bottom: 15px; color: #e74c3c; }" +
                        "        .footer p { line-height: 1.6; margin-bottom: 10px; font-size: 14px; }" +
                        "        .footer .divider { height: 2px; background: linear-gradient(90deg, #e74c3c, #f39c12); border-radius: 1px; margin: 20px 0; }" +
                        "        .footer .school-info { font-style: italic; color: #bdc3c7; }" +
                        "        .highlight { background: linear-gradient(120deg, #ff7675 0%%, #fd79a8 100%%); padding: 2px 6px; border-radius: 4px; color: #ffffff; font-weight: 500; }" +
                        "        .alert-icon { color: #dc3545; font-weight: bold; margin-right: 8px; }" +
                        "        .info-icon { color: #d63031; font-weight: bold; margin-right: 8px; }" +
                        "        .urgent-badge { background: #dc3545; color: white; padding: 4px 8px; border-radius: 12px; font-size: 12px; font-weight: bold; }" +
                        "        @media (max-width: 600px) {" +
                        "            .email-container { margin: 10px; border-radius: 0; }" +
                        "            .info-grid { flex-direction: column; }" +
                        "            .main-content, .greeting { padding: 20px; }" +
                        "        }" +
                        "    </style>" +
                        "</head>" +
                        "<body>" +
                        "    <div class=\"email-container\">" +
                        "        <div class=\"header\">" +
                        "            <div class=\"icon\">🚨</div>" +
                        "            <h1>Thông Báo Y Tế Khẩn Cấp</h1>" +
                        "            <p class=\"subtitle\">Hệ thống quản lý y tế trường học</p>" +
                        "            <span class=\"urgent-badge\">KHẨN CẤP</span>" +
                        "        </div>" +
                        "        <div class=\"content-wrapper\">" +
                        "            <div class=\"greeting\">" +
                        "                <h2>Kính chào Quý Phụ huynh %s!</h2>" +
                        "                <p>Chúng tôi xin gửi đến Quý Phụ huynh thông báo <span class=\"highlight\">khẩn cấp</span> về tình hình y tế của con em.</p>" +
                        "            </div>" +
                        "            <div class=\"main-content\">" +
                        "                <div class=\"content-box\">" +
                        "                    <h3><span class=\"alert-icon\">⚠</span>Chi Tiết Sự Kiện Y Tế</h3>" +
                        "                    <div class=\"content-text\">%s</div>" +
                        "                </div>" +
                        "                <div class=\"info-section\">" +
                        "                    <div class=\"info-title\"><span class=\"info-icon\">ℹ</span>Thông Tin Gửi Thông Báo</div>" +
                        "                    <div class=\"info-grid\">" +
                        "                        <div class=\"info-item\">" +
                        "                            <div class=\"info-label\">📅 Thời gian gửi</div>" +
                        "                            <div class=\"info-value\">%s</div>" +
                        "                        </div>" +
                        "                        <div class=\"info-item\">" +
                        "                            <div class=\"info-label\">👤 Người gửi</div>" +
                        "                            <div class=\"info-value\">%s</div>" +
                        "                        </div>" +
                        "                    </div>" +
                        "                </div>" +
                        "            </div>" +
                        "        </div>" +
                        "        <div class=\"footer\">" +
                        "            <div class=\"footer-content\">" +
                        "                <h3>🏫 Ban Y Tế Trường Học</h3>" +
                        "                <p>Chúng tôi luôn đảm bảo an toàn và sức khỏe cho các em học sinh.</p>" +
                        "                <div class=\"divider\"></div>" +
                        "                <p class=\"school-info\">Vui lòng liên hệ ngay nếu cần hỗ trợ thêm</p>" +
                        "                <p class=\"school-info\">📧 Đây là email tự động, vui lòng không trả lời</p>" +
                        "            </div>" +
                        "        </div>" +
                        "    </div>" +
                        "</body>" +
                        "</html>",


                parent.getFullName() != null ? parent.getFullName() : "Quý Phụ huynh",
                formatContentToHtml(content),
                datetime,
                username
        );
    }


    private String createHtmlContentForHealthCheck(Parent parent, String content, String datetime, String username) {
        return String.format(
                "<!DOCTYPE html>" +
                "<html lang=\"vi\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>Thông báo khám sức khỏe</title>" +
                "    <style>" +
                "        * { margin: 0; padding: 0; box-sizing: border-box; }" +
                "        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f5f5f5; }" +
                "        .email-container { max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 20px rgba(0,0,0,0.1); }" +
                "        .header { background: linear-gradient(135deg, #007bff, #0056b3); color: white; padding: 30px 20px; text-align: center; position: relative; }" +
                "        .header h1 { font-size: 24px; margin-bottom: 8px; position: relative; z-index: 1; }" +
                "        .header .icon { font-size: 48px; margin-bottom: 15px; }" +
                "        .header .subtitle { font-size: 14px; opacity: 0.9; position: relative; z-index: 1; }" +
                "        .content-wrapper { padding: 0; }" +
                "        .greeting { background: #f8f9fa; padding: 25px 30px; border-left: 4px solid #007bff; }" +
                "        .greeting h2 { color: #333; font-size: 18px; margin-bottom: 10px; }" +
                "        .greeting p { color: #666; line-height: 1.6; }" +
                "        .main-content { padding: 30px; }" +
                "        .content-box { background: #f8f9fa; border-radius: 8px; padding: 25px; margin: 20px 0; border-left: 4px solid #28a745; }" +
                "        .content-box h3 { color: #28a745; font-size: 16px; margin-bottom: 15px; }" +
                "        .content-text { color: #555; line-height: 1.8; font-size: 15px; }" +
                "        .info-section { background: linear-gradient(135deg, #e3f2fd, #bbdefb); padding: 25px; margin: 25px 0; border-radius: 10px; }" +
                "        .info-title { color: #1976d2; font-weight: bold; font-size: 16px; margin-bottom: 15px; }" +
                "        .info-grid { display: flex; flex-wrap: wrap; gap: 15px; }" +
                "        .info-item { background: rgba(255,255,255,0.7); padding: 12px 15px; border-radius: 6px; flex: 1; min-width: 200px; }" +
                "        .info-label { font-weight: 600; color: #333; font-size: 13px; margin-bottom: 4px; }" +
                "        .info-value { color: #666; font-size: 14px; }" +
                "        .footer { background: #2c3e50; color: #ecf0f1; padding: 30px; text-align: center; }" +
                "        .footer-content { max-width: 400px; margin: 0 auto; }" +
                "        .footer h3 { font-size: 18px; margin-bottom: 15px; color: #3498db; }" +
                "        .footer p { line-height: 1.6; margin-bottom: 10px; font-size: 14px; }" +
                "        .footer .divider { height: 2px; background: linear-gradient(90deg, #3498db, #2ecc71); border-radius: 1px; margin: 20px 0; }" +
                "        .footer .school-info { font-style: italic; color: #bdc3c7; }" +
                "        .highlight { background: linear-gradient(120deg, #ffeaa7 0%%, #fab1a0 100%%); padding: 2px 6px; border-radius: 4px; color: #2d3436; font-weight: 500; }" +
                "        .checkmark { color: #28a745; font-weight: bold; margin-right: 8px; }" +
                "        .info-icon { color: #1976d2; font-weight: bold; margin-right: 8px; }" +
                "        @media (max-width: 600px) {" +
                "            .email-container { margin: 10px; border-radius: 0; }" +
                "            .info-grid { flex-direction: column; }" +
                "            .main-content, .greeting { padding: 20px; }" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"email-container\">" +
                "        <div class=\"header\">" +
                "            <div class=\"icon\">🏥</div>" +
                "            <h1>Thông Báo Khám Sức Khỏe</h1>" +
                "            <p class=\"subtitle\">Hệ thống quản lý y tế trường học</p>" +
                "        </div>" +
                "        <div class=\"content-wrapper\">" +
                "            <div class=\"greeting\">" +
                "                <h2>Kính chào Quý Phụ huynh %s!</h2>" +
                "                <p>Chúng tôi xin gửi đến Quý Phụ huynh thông báo quan trọng về <span class=\"highlight\">lịch khám sức khỏe</span> của con em.</p>" +
                "            </div>" +
                "            <div class=\"main-content\">" +
                "                <div class=\"content-box\">" +
                "                    <h3><span class=\"checkmark\">✓</span>Chi Tiết Thông Báo</h3>" +
                "                    <div class=\"content-text\">%s</div>" +
                "                </div>" +
                "                <div class=\"info-section\">" +
                "                    <div class=\"info-title\"><span class=\"info-icon\">ℹ</span>Thông Tin Gửi Thông Báo</div>" +
                "                    <div class=\"info-grid\">" +
                "                        <div class=\"info-item\">" +
                "                            <div class=\"info-label\">📅 Thời gian gửi</div>" +
                "                            <div class=\"info-value\">%s</div>" +
                "                        </div>" +
                "                        <div class=\"info-item\">" +
                "                            <div class=\"info-label\">👤 Người gửi</div>" +
                "                            <div class=\"info-value\">%s</div>" +
                "                        </div>" +
                "                    </div>" +
                "                </div>" +
                "            </div>" +
                "        </div>" +
                "        <div class=\"footer\">" +
                "            <div class=\"footer-content\">" +
                "                <h3>🏫 Ban Y Tế Trường Học</h3>" +
                "                <p>Chúng tôi cam kết chăm sóc sức khỏe tốt nhất cho các em học sinh.</p>" +
                "                <div class=\"divider\"></div>" +
                "                <p class=\"school-info\">Hệ thống quản lý y tế hiện đại và chuyên nghiệp</p>" +
                "                <p class=\"school-info\">📧 Đây là email tự động, vui lòng không trả lời</p>" +
                "            </div>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>",
                parent.getFullName() != null ? parent.getFullName() : "Quý Phụ huynh",
                formatContentToHtml(content),
                datetime,
                username
        );
    }

    private String createHtmlContentForHealthCheckStudent(Parent parent, String content, String datetime, String username) {
        return String.format(
                "<!DOCTYPE html>" +
                "<html lang=\"vi\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>Kết quả kiểm tra sức khỏe</title>" +
                "    <style>" +
                "        * { margin: 0; padding: 0; box-sizing: border-box; }" +
                "        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; padding: 20px; }" +
                "        .email-container { max-width: 600px; margin: 0 auto; background: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }" +
                "        .header { background: #28a745; color: white; padding: 30px 25px; text-align: center; }" +
                "        .header h1 { font-size: 24px; margin-bottom: 8px; font-weight: 600; }" +
                "        .header .icon { font-size: 40px; margin-bottom: 12px; }" +
                "        .header .subtitle { font-size: 14px; opacity: 0.9; }" +
                "        .greeting { background: #f8f9fa; padding: 25px; border-left: 4px solid #28a745; }" +
                "        .greeting h2 { color: #28a745; font-size: 20px; margin-bottom: 12px; font-weight: 600; }" +
                "        .greeting p { color: #495057; line-height: 1.6; font-size: 15px; }" +
                "        .main-content { padding: 30px 25px; }" +
                "        .health-results { background: #fff; border: 2px solid #e9ecef; border-radius: 6px; padding: 25px; margin: 20px 0; }" +
                "        .health-results h3 { color: #28a745; font-size: 18px; margin-bottom: 15px; display: flex; align-items: center; }" +
                "        .health-results .icon { color: #28a745; margin-right: 10px; font-size: 20px; }" +
                "        .content-text { color: #495057; line-height: 1.7; font-size: 15px; }" +
                "        .health-metrics { background: #f8f9fa; border-radius: 6px; padding: 20px; margin: 15px 0; }" +
                "        .health-metrics h4 { color: #495057; font-size: 16px; margin-bottom: 15px; font-weight: 600; }" +
                "        .metrics-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; }" +
                "        .metric-item { background: #ffffff; border: 1px solid #dee2e6; padding: 15px; border-radius: 4px; }" +
                "        .metric-label { font-weight: 600; color: #6c757d; font-size: 12px; margin-bottom: 5px; text-transform: uppercase; }" +
                "        .metric-value { color: #495057; font-size: 14px; font-weight: 500; }" +
                "        .health-note { background: #e8f5e8; border: 1px solid #c3e6c3; border-radius: 6px; padding: 20px; margin: 20px 0; }" +
                "        .health-note h4 { color: #155724; font-size: 16px; margin-bottom: 10px; }" +
                "        .health-note p { color: #155724; font-size: 14px; line-height: 1.5; }" +
                "        .contact-info { background: #e7f3ff; border-radius: 6px; padding: 20px; margin: 20px 0; text-align: center; }" +
                "        .contact-info h4 { color: #0c5460; font-size: 16px; margin-bottom: 15px; }" +
                "        .contact-button { background: #007bff; color: white; padding: 10px 20px; border-radius: 4px; text-decoration: none; font-weight: 500; margin: 5px; display: inline-block; transition: background-color 0.2s; }" +
                "        .contact-button:hover { background: #0056b3; }" +
                "        .info-section { background: #f8f9fa; border-radius: 6px; padding: 20px; margin: 20px 0; }" +
                "        .info-title { color: #495057; font-weight: 600; font-size: 16px; margin-bottom: 15px; }" +
                "        .info-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; }" +
                "        .info-item { background: #ffffff; border: 1px solid #dee2e6; padding: 15px; border-radius: 4px; }" +
                "        .info-label { font-weight: 600; color: #6c757d; font-size: 12px; margin-bottom: 5px; text-transform: uppercase; }" +
                "        .info-value { color: #495057; font-size: 14px; }" +
                "        .footer { background: #343a40; color: #ffffff; padding: 25px; text-align: center; }" +
                "        .footer h3 { font-size: 18px; margin-bottom: 15px; color: #ffffff; }" +
                "        .footer p { line-height: 1.6; margin-bottom: 8px; font-size: 14px; color: #adb5bd; }" +
                "        .footer .divider { height: 1px; background: #495057; margin: 15px 0; }" +
                "        .highlight { background: #d4edda; color: #155724; padding: 2px 6px; border-radius: 3px; font-weight: 500; }" +
                "        .bmi-normal { color: #28a745; font-weight: 600; }" +
                "        .bmi-warning { color: #ffc107; font-weight: 600; }" +
                "        .bmi-danger { color: #dc3545; font-weight: 600; }" +
                "        @media (max-width: 600px) {" +
                "            body { padding: 10px; }" +
                "            .email-container { border-radius: 4px; }" +
                "            .metrics-grid, .info-grid { grid-template-columns: 1fr; }" +
                "            .main-content, .greeting { padding: 20px; }" +
                "            .header { padding: 25px 20px; }" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"email-container\">" +
                "        <div class=\"header\">" +
                "            <div class=\"icon\">🩺</div>" +
                "            <h1>Kết Quả Kiểm Tra Sức Khỏe</h1>" +
                "            <p class=\"subtitle\">Hệ thống quản lý y tế trường học</p>" +
                "        </div>" +
                "        <div class=\"greeting\">" +
                "            <h2>Kính chào Quý Phụ huynh %s</h2>" +
                "            <p>Chúng tôi xin gửi đến Quý Phụ huynh <span class=\"highlight\">kết quả kiểm tra sức khỏe</span> chi tiết của con em từ đội ngũ y tế chuyên nghiệp của trường.</p>" +
                "        </div>" +
                "        <div class=\"main-content\">" +
                "            <div class=\"health-results\">" +
                "                <h3><span class=\"icon\">📋</span>Báo Cáo Kết Quả Sức Khỏe</h3>" +
                "                <div class=\"content-text\">%s</div>" +
                "            </div>" +
                "            <div class=\"health-note\">" +
                "                <h4>📌 Lưu ý quan trọng</h4>" +
                "                <p>Vui lòng lưu trữ kết quả này để theo dõi sức khỏe của con em. Nếu có bất kỳ thắc mắc nào về kết quả kiểm tra, xin vui lòng liên hệ trực tiếp với ban y tế nhà trường.</p>" +
                "            </div>" +
                "            <div class=\"contact-info\">" +
                "                <h4>📞 Liên hệ hỗ trợ</h4>" +
                "                <p style=\"color: #0c5460; margin-bottom: 15px;\">Ban y tế luôn sẵn sàng hỗ trợ Quý Phụ huynh</p>" +
                "                <a href=\"tel:+84123456789\" class=\"contact-button\">📞 Gọi điện</a>" +
                "                <a href=\"mailto:ytetrường@school.edu.vn\" class=\"contact-button\">📧 Gửi email</a>" +
                "            </div>" +
                "            <div class=\"info-section\">" +
                "                <div class=\"info-title\">Thông tin gửi báo cáo</div>" +
                "                <div class=\"info-grid\">" +
                "                    <div class=\"info-item\">" +
                "                        <div class=\"info-label\">Thời gian gửi</div>" +
                "                        <div class=\"info-value\">%s</div>" +
                "                    </div>" +
                "                    <div class=\"info-item\">" +
                "                        <div class=\"info-label\">Người gửi</div>" +
                "                        <div class=\"info-value\">%s</div>" +
                "                    </div>" +
                "                </div>" +
                "            </div>" +
                "        </div>" +
                "        <div class=\"footer\">" +
                "            <h3>🏥 Ban Y Tế Trường Học</h3>" +
                "            <p>Chúng tôi cam kết theo dõi và chăm sóc sức khỏe toàn diện cho các em học sinh với đội ngũ y tế chuyên nghiệp.</p>" +
                "            <div class=\"divider\"></div>" +
                "            <p>Sức khỏe của con em là ưu tiên hàng đầu</p>" +
                "            <p>📧 Email tự động - Vui lòng không trả lời trực tiếp</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>",
                parent.getFullName() != null ? parent.getFullName() : "Quý Phụ huynh",
                content, // This will be the HTML content from HealthCheckStudentService.createHtmlContentForHealthCheckStudent
                datetime,
                username
        );
    }

    private String createHtmlContentForHealthCheckConsultation(Parent parent, String content, String datetime, String username) {
        return String.format(
                "<!DOCTYPE html>" +
                "<html lang=\"vi\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>Mời tư vấn y tế</title>" +
                "    <style>" +
                "        * { margin: 0; padding: 0; box-sizing: border-box; }" +
                "        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; padding: 20px; }" +
                "        .email-container { max-width: 600px; margin: 0 auto; background: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }" +
                "        .header { background: #2c5aa0; color: white; padding: 30px 25px; text-align: center; }" +
                "        .header h1 { font-size: 24px; margin-bottom: 8px; font-weight: 600; }" +
                "        .header .icon { font-size: 40px; margin-bottom: 12px; }" +
                "        .header .subtitle { font-size: 14px; opacity: 0.9; }" +
                "        .greeting { background: #f8f9fa; padding: 25px; border-left: 4px solid #2c5aa0; }" +
                "        .greeting h2 { color: #2c5aa0; font-size: 20px; margin-bottom: 12px; font-weight: 600; }" +
                "        .greeting p { color: #495057; line-height: 1.6; font-size: 15px; }" +
                "        .main-content { padding: 30px 25px; }" +
                "        .consultation-info { background: #fff; border: 2px solid #e9ecef; border-radius: 6px; padding: 25px; margin: 20px 0; }" +
                "        .consultation-info h3 { color: #2c5aa0; font-size: 18px; margin-bottom: 15px; display: flex; align-items: center; }" +
                "        .consultation-info .icon { color: #2c5aa0; margin-right: 10px; font-size: 20px; }" +
                "        .content-text { color: #495057; line-height: 1.7; font-size: 15px; }" +
                "        .important-note { background: #fff3cd; border: 1px solid #ffeaa7; border-radius: 6px; padding: 20px; margin: 20px 0; }" +
                "        .important-note h4 { color: #856404; font-size: 16px; margin-bottom: 10px; }" +
                "        .important-note p { color: #856404; font-size: 14px; line-height: 1.5; }" +
                "        .contact-info { background: #e8f4f8; border-radius: 6px; padding: 20px; margin: 20px 0; text-align: center; }" +
                "        .contact-info h4 { color: #0c5460; font-size: 16px; margin-bottom: 15px; }" +
                "        .contact-button { background: #28a745; color: white; padding: 10px 20px; border-radius: 4px; text-decoration: none; font-weight: 500; margin: 5px; display: inline-block; transition: background-color 0.2s; }" +
                "        .contact-button:hover { background: #218838; }" +
                "        .info-section { background: #f8f9fa; border-radius: 6px; padding: 20px; margin: 20px 0; }" +
                "        .info-title { color: #495057; font-weight: 600; font-size: 16px; margin-bottom: 15px; }" +
                "        .info-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 15px; }" +
                "        .info-item { background: #ffffff; border: 1px solid #dee2e6; padding: 15px; border-radius: 4px; }" +
                "        .info-label { font-weight: 600; color: #6c757d; font-size: 12px; margin-bottom: 5px; text-transform: uppercase; }" +
                "        .info-value { color: #495057; font-size: 14px; }" +
                "        .footer { background: #343a40; color: #ffffff; padding: 25px; text-align: center; }" +
                "        .footer h3 { font-size: 18px; margin-bottom: 15px; color: #ffffff; }" +
                "        .footer p { line-height: 1.6; margin-bottom: 8px; font-size: 14px; color: #adb5bd; }" +
                "        .footer .divider { height: 1px; background: #495057; margin: 15px 0; }" +
                "        .highlight { background: #e7f3ff; color: #0c5460; padding: 2px 6px; border-radius: 3px; font-weight: 500; }" +
                "        @media (max-width: 600px) {" +
                "            body { padding: 10px; }" +
                "            .email-container { border-radius: 4px; }" +
                "            .info-grid { grid-template-columns: 1fr; }" +
                "            .main-content, .greeting { padding: 20px; }" +
                "            .header { padding: 25px 20px; }" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"email-container\">" +
                "        <div class=\"header\">" +
                "            <div class=\"icon\">🏥</div>" +
                "            <h1>Thư Mời Tư Vấn Y Tế</h1>" +
                "            <p class=\"subtitle\">Hệ thống quản lý y tế trường học</p>" +
                "        </div>" +
                "        <div class=\"greeting\">" +
                "            <h2>Kính chào Quý Phụ huynh %s</h2>" +
                "            <p>Chúng tôi xin gửi đến Quý Phụ huynh lời mời tham gia <span class=\"highlight\">buổi tư vấn y tế</span> quan trọng cho con em.</p>" +
                "        </div>" +
                "        <div class=\"main-content\">" +
                "            <div class=\"consultation-info\">" +
                "                <h3><span class=\"icon\">📋</span>Thông Tin Tư Vấn</h3>" +
                "                <div class=\"content-text\">%s</div>" +
                "            </div>" +
                "            <div class=\"important-note\">" +
                "                <h4>⚠️ Lưu ý quan trọng</h4>" +
                "                <p>Vui lòng sắp xếp thời gian tham gia đầy đủ để đảm bảo hiệu quả tư vấn tốt nhất cho con em. Nếu có thay đổi lịch hẹn, xin vui lòng liên hệ trước ít nhất 24 giờ.</p>" +
                "            </div>" +
                "            <div class=\"contact-info\">" +
                "                <h4>📞 Liên hệ hỗ trợ</h4>" +
                "                <p style=\"color: #0c5460; margin-bottom: 15px;\">Đội ngũ y tế luôn sẵn sàng hỗ trợ Quý Phụ huynh</p>" +
                "                <a href=\"tel:+84123456789\" class=\"contact-button\">📞 Gọi điện</a>" +
                "                <a href=\"mailto:ytetrường@school.edu.vn\" class=\"contact-button\">📧 Gửi email</a>" +
                "            </div>" +
                "            <div class=\"info-section\">" +
                "                <div class=\"info-title\">Thông tin gửi thông báo</div>" +
                "                <div class=\"info-grid\">" +
                "                    <div class=\"info-item\">" +
                "                        <div class=\"info-label\">Thời gian gửi</div>" +
                "                        <div class=\"info-value\">%s</div>" +
                "                    </div>" +
                "                    <div class=\"info-item\">" +
                "                        <div class=\"info-label\">Người gửi</div>" +
                "                        <div class=\"info-value\">%s</div>" +
                "                    </div>" +
                "                </div>" +
                "            </div>" +
                "        </div>" +
                "        <div class=\"footer\">" +
                "            <h3>🏫 Ban Y Tế Trường Học</h3>" +
                "            <p>Chúng tôi cam kết mang đến dịch vụ chăm sóc sức khỏe chất lượng cao cho các em học sinh.</p>" +
                "            <div class=\"divider\"></div>" +
                "            <p>Sức khỏe của con em là ưu tiên hàng đầu</p>" +
                "            <p>📧 Email tự động - Vui lòng không trả lời trực tiếp</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>",
                parent.getFullName() != null ? parent.getFullName() : "Quý Phụ huynh",
                formatContentToHtml(content),
                datetime,
                username
        );
    }


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
