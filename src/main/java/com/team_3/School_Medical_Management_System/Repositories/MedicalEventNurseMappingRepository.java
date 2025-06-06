package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.MedicalEventNurseMapping;
import com.team_3.School_Medical_Management_System.Model.MedicalEventNurseMappingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalEventNurseMappingRepository extends JpaRepository<MedicalEventNurseMapping, MedicalEventNurseMappingId> {
  //  Optional<MedicalEventNurseMapping> findByeventID_eventID(Integer eventId);
}
