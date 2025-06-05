package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.MedicalEventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalEventTypeRepository extends JpaRepository<MedicalEventType, Integer> {
    Optional<MedicalEventType> findByTypeName(String typeName);
}
