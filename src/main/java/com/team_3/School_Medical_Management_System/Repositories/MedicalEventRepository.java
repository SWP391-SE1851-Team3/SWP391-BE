package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.MedicalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface MedicalEventRepository extends JpaRepository<MedicalEvent, Integer> {

    List<MedicalEvent> findByParentId(Integer parentId);
   // List<MedicalEvent> findByEventDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
