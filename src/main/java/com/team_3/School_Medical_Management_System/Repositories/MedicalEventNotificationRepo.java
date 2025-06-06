package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.MedicalEventNotification;
import com.team_3.School_Medical_Management_System.Model.MedicalEventNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalEventNotificationRepo extends JpaRepository<MedicalEventNotification, MedicalEventNotificationId> {
  //  Optional<MedicalEventNotification> findByeventID_eventID(Integer eventId);

}
