package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.MedicalEvent;
import com.team_3.School_Medical_Management_System.Model.Parent;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface MedicalEventRepository extends JpaRepository<MedicalEvent, Integer> {

    //List<MedicalEvent> findByParent_ParentId(Integer parentId);
    //SELECT me FROM MedicalEvent me WHERE me.parent.parentId = :parentId
  //  List<MedicalEvent> findByParent(Integer parentID);
   // List<MedicalEvent> findByEventDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
