package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.MedicalEventNotification;
import com.team_3.School_Medical_Management_System.Model.MedicalEventNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalEventNotificationRepo extends JpaRepository<MedicalEventNotification, MedicalEventNotificationId> {
    List<MedicalEventNotification> findByParentIDParentID(Integer parentId);
    List<MedicalEventNotification> findByEventIDEventID(Integer eventId);
}
