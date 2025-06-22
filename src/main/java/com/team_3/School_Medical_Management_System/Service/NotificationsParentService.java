package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.InterfaceRepo.NotificationsParentRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ParentRepository;
import com.team_3.School_Medical_Management_System.Model.NotificationsParent;
import com.team_3.School_Medical_Management_System.Model.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationsParentService {
    @Autowired
    private NotificationsParentRepository notificationsParentRepository;

    @Autowired
    private ParentRepository parentRepository;

    public NotificationsParentService(NotificationsParentRepository notificationsParentRepository) {
        this.notificationsParentRepository = notificationsParentRepository;
    }


    public Integer createAutoNotification(Integer parentId, String title, String content) {
        NotificationsParent notification = new NotificationsParent();
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phụ huynh với ID: " + parentId));
        notification.setParent(parent);
        notification.setTitle(title);
        notification.setContent(content);
        notification.setStatus(false); // chưa gửi

        NotificationsParent saved = notificationsParentRepository.save(notification);
        return saved.getNotificationId();
    }
}

