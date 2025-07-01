package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Notifications_MedicalEventDetails;
import com.team_3.School_Medical_Management_System.Model.Notifications_MedicalEventDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationsMedicalEventDetailsRepository extends JpaRepository<Notifications_MedicalEventDetails, Notifications_MedicalEventDetailsId> {

@Modifying
    @Query("DELETE FROM Notifications_MedicalEventDetails n WHERE n.medicalEvent.eventID = :medicalId")
    void deleteByMedicalEvent_EventID(@Param("medicalId") Integer medicalId);
//public void deleteByMedicalEvent_EventID(Integer medicalId);

    // Additional query methods can be defined here if needed

}
