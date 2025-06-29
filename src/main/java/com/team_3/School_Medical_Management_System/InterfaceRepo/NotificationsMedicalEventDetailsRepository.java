package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Notifications_MedicalEventDetails;
import com.team_3.School_Medical_Management_System.Model.Notifications_MedicalEventDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationsMedicalEventDetailsRepository extends JpaRepository<Notifications_MedicalEventDetails, Notifications_MedicalEventDetailsId> {
public void deleteByMedicalEvent_EventID(Integer medicalId);

    // Additional query methods can be defined here if needed

}
