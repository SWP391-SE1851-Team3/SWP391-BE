package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.MedicalEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalEventRepository extends JpaRepository<MedicalEvent, Integer> {
}
