package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.MedicalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MedicalEventRepo extends JpaRepository<MedicalEvent, Integer> {
    //List<MedicalEvent> findByParent_ParentID(Integer parentId);
    List<MedicalEvent> findByParentParentIDAndIsEmergencyTrue(int parentId);

}
