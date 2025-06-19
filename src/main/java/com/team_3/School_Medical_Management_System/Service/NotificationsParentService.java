package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.NotificationsParentDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.NotificationsParentRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ParentRepository;
import com.team_3.School_Medical_Management_System.Model.NotificationsParent;
import com.team_3.School_Medical_Management_System.Model.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationsParentService {

    @Autowired
    private NotificationsParentRepository notificationsParentRepository; // Repository cho NotificationsParent

    @Autowired
    private ParentRepository parentRepository; // Repository cho Parent

    // Gửi thông báo cho phụ huynh
    public NotificationsParentDTO sendNotification(NotificationsParentDTO dto) {
        // Tạo mới thông báo
        NotificationsParent notification = new NotificationsParent();
        notification.setTitle(dto.getTitle());
        notification.setContent(dto.getContent());
        notification.setStatus(dto.isStatus());
        notification.setCreateAt(LocalDateTime.now());
        notification.setType(dto.getType());

        // Kiểm tra phụ huynh
        Optional<Parent> parent = parentRepository.findById(dto.getParentId());
         // Nếu phụ huynh không tồn tại, ném ngoại lệ
         // Thay thế RuntimeException bằng một ngoại lệ cụ thể hơn nếu cần
        if (parent.isEmpty()) {
            throw new RuntimeException("Phụ huynh không tồn tại");
        }
        notification.setParent(parent.get());

        // Lưu thông báo
        NotificationsParent savedNotification = notificationsParentRepository.save(notification);

        // Tạo DTO để trả về
        NotificationsParentDTO result = new NotificationsParentDTO();
        result.setNotificationId(savedNotification.getNotificationId());
        result.setParentId(savedNotification.getParent().getParentID());
        result.setTitle(savedNotification.getTitle());
        result.setContent(savedNotification.getContent());
        result.setStatus(savedNotification.isStatus());
        result.setCreateAt(savedNotification.getCreateAt());
        result.setType(savedNotification.getType());

        return result;
    }

    // Get all notifications for a parent
    public List<NotificationsParent> getNotificationsForParent(int parentId) {
        Optional<Parent> parent = parentRepository.findById(parentId);

        if (parent.isPresent()) {
            return notificationsParentRepository.findByParent(parent.get());
        }

        return List.of();
    }

    // Get unread notifications for a parent
    public List<NotificationsParent> getUnreadNotificationsForParent(int parentId) {
        Optional<Parent> parent = parentRepository.findById(parentId);

        if (parent.isPresent()) {
            return notificationsParentRepository.findByParentAndStatus(parent.get(), false);
        }

        return List.of();
    }

    // Mark notification as read
    public NotificationsParent markNotificationAsRead(int notificationId) {
        Optional<NotificationsParent> optionalNotification = notificationsParentRepository.findById(notificationId);

        if (optionalNotification.isPresent()) {
            NotificationsParent notification = optionalNotification.get();
            notification.setStatus(true);
            return notificationsParentRepository.save(notification);
        }

        return null;
    }

    // Create a new notification for a parent
    public NotificationsParent createNotification(int parentId, String title, String content, String type) {
        Optional<Parent> parent = parentRepository.findById(parentId);

        if (parent.isPresent()) {
            NotificationsParent notification = new NotificationsParent();
            notification.setParent(parent.get());
            notification.setTitle(title);
            notification.setContent(content);
            notification.setCreateAt(LocalDateTime.now());
            notification.setStatus(false);
            notification.setType(type);

            return notificationsParentRepository.save(notification);
        }

        return null;
    }

    // Convert entity to DTO
    public NotificationsParentDTO convertToDTO(NotificationsParent notification) {
        NotificationsParentDTO dto = new NotificationsParentDTO();
        dto.setNotificationId(notification.getNotificationId());
        dto.setParentId(notification.getParent().getParentID());
        dto.setTitle(notification.getTitle());
        dto.setContent(notification.getContent());
        dto.setCreateAt(notification.getCreateAt());
        dto.setStatus(notification.isStatus());
        dto.setType(notification.getType());
        return dto;
    }
}
