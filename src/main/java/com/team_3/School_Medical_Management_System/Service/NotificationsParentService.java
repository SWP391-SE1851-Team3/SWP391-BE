package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.NotificationsParentDTO;
import com.team_3.School_Medical_Management_System.Model.NotificationsParent;
import com.team_3.School_Medical_Management_System.Model.Parent;
import com.team_3.School_Medical_Management_System.Repositories.NotificationsParentRepository;
import com.team_3.School_Medical_Management_System.Repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        return result;
    }

}
