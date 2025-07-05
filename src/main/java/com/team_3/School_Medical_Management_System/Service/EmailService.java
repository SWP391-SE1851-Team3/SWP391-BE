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
                "        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f0f8ff; }" +
                "        .email-container { max-width: 650px; margin: 20px auto; background: #ffffff; border-radius: 15px; overflow: hidden; box-shadow: 0 8px 32px rgba(0,0,0,0.12); }" +
                "        .header { background: linear-gradient(135deg, #1976d2, #1565c0); color: white; padding: 35px 25px; text-align: center; position: relative; }" +
                "        .header::before { content: ''; position: absolute; top: 0; left: 0; right: 0; bottom: 0; background: url('data:image/svg+xml,<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 100 100\"><circle cx=\"20\" cy=\"20\" r=\"2\" fill=\"white\" opacity=\"0.1\"/><circle cx=\"80\" cy=\"30\" r=\"1.5\" fill=\"white\" opacity=\"0.1\"/><circle cx=\"60\" cy=\"70\" r=\"1\" fill=\"white\" opacity=\"0.1\"/></svg>'); }" +
                "        .header h1 { font-size: 26px; margin-bottom: 10px; position: relative; z-index: 1; }" +
                "        .header .icon { font-size: 52px; margin-bottom: 15px; }" +
                "        .header .subtitle { font-size: 15px; opacity: 0.9; position: relative; z-index: 1; }" +
                "        .content-wrapper { padding: 0; }" +
                "        .greeting { background: linear-gradient(135deg, #e3f2fd, #f3e5f5); padding: 30px 35px; border-left: 5px solid #1976d2; }" +
                "        .greeting h2 { color: #1565c0; font-size: 20px; margin-bottom: 12px; }" +
                "        .greeting p { color: #37474f; line-height: 1.7; font-size: 15px; }" +
                "        .main-content { padding: 35px; }" +
                "        .content-box { background: linear-gradient(135deg, #f8f9fa, #e9ecef); border-radius: 12px; padding: 30px; margin: 25px 0; border-left: 5px solid #28a745; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }" +
                "        .content-box h3 { color: #1976d2; font-size: 18px; margin-bottom: 20px; display: flex; align-items: center; }" +
                "        .content-text { color: #424242; line-height: 1.8; font-size: 15px; }" +
                "        .health-results { background: #ffffff; border-radius: 10px; padding: 25px; margin: 20px 0; box-shadow: 0 2px 8px rgba(0,0,0,0.08); }" +
                "        .info-section { background: linear-gradient(135deg, #e8f5e8, #f0f8f0); padding: 28px; margin: 28px 0; border-radius: 12px; border: 1px solid #c8e6c9; }" +
                "        .info-title { color: #2e7d32; font-weight: bold; font-size: 17px; margin-bottom: 18px; display: flex; align-items: center; }" +
                "        .info-grid { display: flex; flex-wrap: wrap; gap: 18px; }" +
                "        .info-item { background: rgba(255,255,255,0.9); padding: 15px 18px; border-radius: 8px; flex: 1; min-width: 220px; border: 1px solid #e0e0e0; }" +
                "        .info-label { font-weight: 600; color: #1976d2; font-size: 13px; margin-bottom: 6px; text-transform: uppercase; letter-spacing: 0.5px; }" +
                "        .info-value { color: #424242; font-size: 15px; font-weight: 500; }" +
                "        .footer { background: linear-gradient(135deg, #263238, #37474f); color: #ecf0f1; padding: 35px; text-align: center; }" +
                "        .footer-content { max-width: 450px; margin: 0 auto; }" +
                "        .footer h3 { font-size: 20px; margin-bottom: 18px; color: #4fc3f7; }" +
                "        .footer p { line-height: 1.7; margin-bottom: 12px; font-size: 14px; }" +
                "        .footer .divider { height: 3px; background: linear-gradient(90deg, #4fc3f7, #81c784); border-radius: 2px; margin: 25px 0; }" +
                "        .footer .school-info { font-style: italic; color: #b0bec5; font-size: 13px; }" +
                "        .highlight { background: linear-gradient(120deg, #4fc3f7 0%%, #81c784 100%%); padding: 3px 8px; border-radius: 5px; color: #ffffff; font-weight: 600; }" +
                "        .health-icon { color: #1976d2; font-weight: bold; margin-right: 10px; font-size: 18px; }" +
                "        .info-icon { color: #2e7d32; font-weight: bold; margin-right: 10px; font-size: 16px; }" +
                "        .results-badge { background: #1976d2; color: white; padding: 6px 12px; border-radius: 15px; font-size: 12px; font-weight: bold; display: inline-block; margin-bottom: 15px; }" +
                "        .note-section { background: #fff3e0; border-left: 4px solid #ff9800; padding: 20px; margin: 20px 0; border-radius: 0 8px 8px 0; }" +
                "        .note-section p { color: #e65100; font-weight: 500; margin: 0; }" +
                "        @media (max-width: 600px) {" +
                "            .email-container { margin: 10px; border-radius: 0; }" +
                "            .info-grid { flex-direction: column; }" +
                "            .main-content, .greeting { padding: 25px 20px; }" +
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
                "            <span class=\"results-badge\">KẾT QUẢ CHÍNH THỨC</span>" +
                "        </div>" +
                "        <div class=\"content-wrapper\">" +
                "            <div class=\"greeting\">" +
                "                <h2>Kính chào Quý Phụ huynh %s!</h2>" +
                "                <p>Chúng tôi xin gửi đến Quý Phụ huynh <span class=\"highlight\">kết quả kiểm tra sức khỏe</span> chi tiết của con em. Đây là báo cáo đầy đủ và chính xác từ đội ngũ y tế chuyên nghiệp của trường.</p>" +
                "            </div>" +
                "            <div class=\"main-content\">" +
                "                <div class=\"content-box\">" +
                "                    <h3><span class=\"health-icon\">📋</span>Báo Cáo Kết Quả Kiểm Tra Sức Khỏe</h3>" +
                "                    <div class=\"health-results\">" +
                "                        %s" +
                "                    </div>" +
                "                </div>" +
                "                <div class=\"note-section\">" +
                "                    <p><strong>📌 Lưu ý quan trọng:</strong> Vui lòng liên hệ với nhà trường nếu Quý phụ huynh có bất kỳ thắc mắc nào về kết quả kiểm tra sức khỏe của con em.</p>" +
                "                </div>" +
                "                <div class=\"info-section\">" +
                "                    <div class=\"info-title\"><span class=\"info-icon\">ℹ️</span>Thông Tin Gửi Báo Cáo</div>" +
                "                    <div class=\"info-grid\">" +
                "                        <div class=\"info-item\">" +
                "                            <div class=\"info-label\">📅 Thời gian gửi</div>" +
                "                            <div class=\"info-value\">%s</div>" +
                "                        </div>" +
                "                        <div class=\"info-item\">" +
                "                            <div class=\"info-label\">👤 Đơn vị gửi</div>" +
                "                            <div class=\"info-value\">%s</div>" +
                "                        </div>" +
                "                    </div>" +
                "                </div>" +
                "            </div>" +
                "        </div>" +
                "        <div class=\"footer\">" +
                "            <div class=\"footer-content\">" +
                "                <h3>🏥 Ban Y Tế Trường Học</h3>" +
                "                <p>Chúng tôi cam kết theo dõi và chăm sóc sức khỏe toàn diện cho các em học sinh với đội ngũ y tế chuyên nghiệp và trang thiết bị hiện đại.</p>" +
                "                <div class=\"divider\"></div>" +
                "                <p class=\"school-info\">Hệ thống quản lý y tế thông minh và chuyên nghiệp</p>" +
                "                <p class=\"school-info\">📧 Đây là email tự động từ hệ thống, vui lòng không trả lời trực tiếp</p>" +
                "                <p class=\"school-info\">📞 Liên hệ: Ban Y tế nhà trường để được hỗ trợ thêm</p>" +
                "            </div>" +
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
                "    <title>Thông báo mời tư vấn y tế</title>" +
                "    <style>" +
                "        * { margin: 0; padding: 0; box-sizing: border-box; }" +
                "        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f0f8ff; }" +
                "        .email-container { max-width: 650px; margin: 20px auto; background: #ffffff; border-radius: 15px; overflow: hidden; box-shadow: 0 8px 32px rgba(0,0,0,0.12); }" +
                "        .header { background: linear-gradient(135deg, #ff9800, #f57c00); color: white; padding: 35px 25px; text-align: center; position: relative; }" +
                "        .header::before { content: ''; position: absolute; top: 0; left: 0; right: 0; bottom: 0; background: url('data:image/svg+xml,<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 100 100\"><circle cx=\"20\" cy=\"20\" r=\"2\" fill=\"white\" opacity=\"0.1\"/><circle cx=\"80\" cy=\"30\" r=\"1.5\" fill=\"white\" opacity=\"0.1\"/><circle cx=\"60\" cy=\"70\" r=\"1\" fill=\"white\" opacity=\"0.1\"/></svg>'); }" +
                "        .header h1 { font-size: 26px; margin-bottom: 10px; position: relative; z-index: 1; }" +
                "        .header .icon { font-size: 52px; margin-bottom: 15px; }" +
                "        .header .subtitle { font-size: 15px; opacity: 0.9; position: relative; z-index: 1; }" +
                "        .content-wrapper { padding: 0; }" +
                "        .greeting { background: linear-gradient(135deg, #fff3e0, #ffe0b2); padding: 30px 35px; border-left: 5px solid #ff9800; }" +
                "        .greeting h2 { color: #e65100; font-size: 20px; margin-bottom: 12px; }" +
                "        .greeting p { color: #37474f; line-height: 1.7; font-size: 15px; }" +
                "        .main-content { padding: 35px; }" +
                "        .content-box { background: linear-gradient(135deg, #f8f9fa, #e9ecef); border-radius: 12px; padding: 30px; margin: 25px 0; border-left: 5px solid #2196f3; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }" +
                "        .content-box h3 { color: #ff9800; font-size: 18px; margin-bottom: 20px; display: flex; align-items: center; }" +
                "        .content-text { color: #424242; line-height: 1.8; font-size: 15px; background: #ffffff; padding: 20px; border-radius: 8px; border: 1px solid #e0e0e0; }" +
                "        .info-section { background: linear-gradient(135deg, #e8f5e8, #f0f8f0); padding: 28px; margin: 28px 0; border-radius: 12px; border: 1px solid #c8e6c9; }" +
                "        .info-title { color: #2e7d32; font-weight: bold; font-size: 17px; margin-bottom: 18px; display: flex; align-items: center; }" +
                "        .info-grid { display: flex; flex-wrap: wrap; gap: 18px; }" +
                "        .info-item { background: rgba(255,255,255,0.9); padding: 15px 18px; border-radius: 8px; flex: 1; min-width: 220px; border: 1px solid #e0e0e0; }" +
                "        .info-label { font-weight: 600; color: #ff9800; font-size: 13px; margin-bottom: 6px; text-transform: uppercase; letter-spacing: 0.5px; }" +
                "        .info-value { color: #424242; font-size: 15px; font-weight: 500; }" +
                "        .consultation-invitation { background: #fff3e0; border: 2px solid #ff9800; padding: 25px; margin: 20px 0; border-radius: 12px; text-align: center; }" +
                "        .consultation-invitation h4 { color: #e65100; font-weight: 600; margin-bottom: 12px; font-size: 18px; }" +
                "        .consultation-invitation p { color: #bf360c; font-weight: 500; margin: 8px 0; line-height: 1.6; }" +
                "        .invitation-details { background: #e3f2fd; border-left: 4px solid #2196f3; padding: 25px; margin: 20px 0; border-radius: 0 8px 8px 0; }" +
                "        .invitation-details h4 { color: #1565c0; font-weight: 600; margin-bottom: 12px; font-size: 16px; }" +
                "        .invitation-details p { color: #0d47a1; font-weight: 500; margin: 8px 0; line-height: 1.6; }" +
                "        .footer { background: linear-gradient(135deg, #e65100, #ff9800); color: #ffffff; padding: 35px; text-align: center; }" +
                "        .footer-content { max-width: 450px; margin: 0 auto; }" +
                "        .footer h3 { font-size: 20px; margin-bottom: 18px; color: #fff3e0; }" +
                "        .footer p { line-height: 1.7; margin-bottom: 12px; font-size: 14px; }" +
                "        .footer .divider { height: 3px; background: linear-gradient(90deg, #fff3e0, #ffcc02); border-radius: 2px; margin: 25px 0; }" +
                "        .footer .school-info { font-style: italic; color: #ffe0b2; font-size: 13px; }" +
                "        .highlight { background: linear-gradient(120deg, #ff9800 0%%, #ffab40 100%%); padding: 3px 8px; border-radius: 5px; color: #ffffff; font-weight: 600; }" +
                "        .consultation-icon { color: #ff9800; font-weight: bold; margin-right: 10px; font-size: 18px; }" +
                "        .info-icon { color: #2e7d32; font-weight: bold; margin-right: 10px; font-size: 16px; }" +
                "        .invitation-badge { background: #ff9800; color: white; padding: 6px 12px; border-radius: 15px; font-size: 12px; font-weight: bold; display: inline-block; margin-bottom: 15px; }" +
                "        .important-note { background: #fff3e0; border: 2px solid #ff9800; padding: 20px; margin: 20px 0; border-radius: 10px; text-align: center; }" +
                "        .important-note p { color: #e65100; font-weight: 600; margin: 0; font-size: 15px; }" +
                "        @media (max-width: 600px) {" +
                "            .email-container { margin: 10px; border-radius: 0; }" +
                "            .info-grid { flex-direction: column; }" +
                "            .main-content, .greeting { padding: 25px 20px; }" +
                "            .header { padding: 25px 20px; }" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"email-container\">" +
                "        <div class=\"header\">" +
                "            <div class=\"icon\">👨‍⚕️</div>" +
                "            <h1>Thông Báo Mời Tư Vấn Y Tế</h1>" +
                "            <p class=\"subtitle\">Hệ thống quản lý y tế trường học</p>" +
                "            <span class=\"invitation-badge\">CẦN TƯ VẤN</span>" +
                "        </div>" +
                "        <div class=\"content-wrapper\">" +
                "            <div class=\"greeting\">" +
                "                <h2>Kính chào Quý Phụ huynh %s!</h2>" +
                "                <p>Chúng tôi xin gửi đến Quý Phụ huynh lời mời tham gia <span class=\"highlight\">buổi tư vấn y tế</span> cho con em. Đây là cơ hội quan trọng để thảo luận về tình hình sức khỏe và nhận được hướng dẫn chuyên nghiệp từ đội ngũ y tế của trường.</p>" +
                "            </div>" +
                "            <div class=\"main-content\">" +
                "                <div class=\"content-box\">" +
                "                    <h3><span class=\"consultation-icon\">📋</span>Thông Tin Lời Mời Tư Vấn Y Tế</h3>" +
                "                    <div class=\"content-text\">" +
                "                        %s" +
                "                    </div>" +
                "                </div>" +
                "                <div class=\"consultation-invitation\">" +
                "                    <h4>📅 Lời Mời Tham Gia Buổi Tư Vấn Y Tế</h4>" +
                "                    <p><strong>Kính mời Quý phụ huynh sắp xếp thời gian tham gia buổi tư vấn y tế quan trọng này.</strong></p>" +
                "                    <p>Buổi tư vấn sẽ giúp con em nhận được sự chăm sóc y tế tốt nhất và hướng dẫn phù hợp.</p>" +
                "                </div>" +
                "                <div class=\"invitation-details\">" +
                "                    <h4>📋 Lưu ý quan trọng cho buổi tư vấn:</h4>" +
                "                    <p>• Vui lòng mang theo sổ sức khỏe và các giấy tờ y tế liên quan của con em</p>" +
                "                    <p>• Chuẩn bị danh sách các câu hỏi muốn tư vấn với đội ngũ y tế</p>" +
                "                    <p>• Liên hệ với nhà trường để xác nhận thời gian tham gia</p>" +
                "                </div>" +
                "                <div class=\"info-section\">" +
                "                    <div class=\"info-title\"><span class=\"info-icon\">ℹ️</span>Thông Tin Gửi Thông Báo</div>" +
                "                    <div class=\"info-grid\">" +
                "                        <div class=\"info-item\">" +
                "                            <div class=\"info-label\">📅 Thời gian gửi</div>" +
                "                            <div class=\"info-value\">%s</div>" +
                "                        </div>" +
                "                        <div class=\"info-item\">" +
                "                            <div class=\"info-label\">👤 Đơn vị gửi</div>" +
                "                            <div class=\"info-value\">%s</div>" +
                "                        </div>" +
                "                    </div>" +
                "                </div>" +
                "            </div>" +
                "        </div>" +
                "        <div class=\"footer\">" +
                "            <div class=\"footer-content\">" +
                "                <h3>🏥 Ban Y Tế Trường Học</h3>" +
                "                <p>Chúng tôi cam kết cung cấp dịch vụ tư vấn y tế chất lượng cao và chăm sóc sức khỏe toàn diện cho các em học sinh với đội ngũ chuyên gia y tế giàu kinh nghiệm.</p>" +
                "                <div class=\"divider\"></div>" +
                "                <p class=\"school-info\">Hệ thống tư vấn y tế chuyên nghiệp và tận tâm</p>" +
                "                <p class=\"school-info\">📧 Đây là email tự động từ hệ thống, vui lòng không trả lời trực tiếp</p>" +
                "                <p class=\"school-info\">📞 Liên hệ: Ban Y tế nhà trường để xác nhận lịch hẹn</p>" +
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
