package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalEvent_Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalEvent_NurseRepo extends JpaRepository<MedicalEvent_Nurse, Integer> {
    void deleteByMedicalEvent_EventID(Integer eventId);
}
