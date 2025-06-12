package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.NotificationsParentDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicalEventRepo;
import com.team_3.School_Medical_Management_System.InterfaceRepo.NotificationsMedicalEventDetailsRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.NotificationsParentRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ParentRepository;
import com.team_3.School_Medical_Management_System.Model.MedicalEvent;
import com.team_3.School_Medical_Management_System.Model.NotificationsMedicalEventDetails;
import com.team_3.School_Medical_Management_System.Model.NotificationsParent;
import com.team_3.School_Medical_Management_System.Model.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NotificationsParentService {


    @Autowired
    private NotificationsParentRepository notificationsParentRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private MedicalEventRepo medicalEventRepository;

    @Autowired
    private NotificationsMedicalEventDetailsRepository notificationsMedicalEventDetailsRepository;
    // Gửi thông báo cho phụ huynh
    public NotificationsParentDTO sendNotification(Integer parentId, Integer eventId,String content,boolean status) {

        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Phụ huynh với ID " + parentId + " không tồn tại"));

        // Kiểm tra sự kiện y tế
        MedicalEvent medicalEvent = medicalEventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Sự kiện y tế với ID " + eventId + " không tồn tại"));
        // Tạo mới thông báo


        NotificationsParent notification = new NotificationsParent();
        notification.setParent(parent);
notification.setContent(content);
        notification.setStatus(status);
        notification.setTitle("Thông báo sự cố y tế ");

        notification.setCreateAt(LocalDateTime.now());
        NotificationsParent savedNotification = notificationsParentRepository.save(notification);




        NotificationsMedicalEventDetails details = new NotificationsMedicalEventDetails();
        details.setParentId(parentId);
        details.setEventId(eventId);
        details.setTitle(notification.getTitle());
        details.setContent(notification.getContent());
        details.setParent(parent);
        details.setMedicalEvent(medicalEvent);
        notificationsMedicalEventDetailsRepository.save(details);

        // Cập nhật trạng thái sự kiện
        medicalEvent.setHasParentBeenInformed(true);
        medicalEventRepository.save(medicalEvent);

        // Chuyển đổi sang DTO
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
