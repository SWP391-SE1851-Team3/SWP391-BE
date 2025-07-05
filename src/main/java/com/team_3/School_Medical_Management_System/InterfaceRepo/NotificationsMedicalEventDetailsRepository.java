package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Notifications_MedicalEventDetails;
import com.team_3.School_Medical_Management_System.Model.Notifications_MedicalEventDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationsMedicalEventDetailsRepository extends JpaRepository<Notifications_MedicalEventDetails, Notifications_MedicalEventDetailsId> {



}
