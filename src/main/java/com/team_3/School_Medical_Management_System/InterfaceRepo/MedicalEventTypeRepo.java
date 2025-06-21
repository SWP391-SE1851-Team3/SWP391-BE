package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalEventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalEventTypeRepo extends JpaRepository<MedicalEventType,Integer> {
}
