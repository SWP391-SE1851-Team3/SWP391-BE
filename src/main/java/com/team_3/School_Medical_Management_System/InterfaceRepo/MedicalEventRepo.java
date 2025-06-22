package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalEvent;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalEventRepo extends JpaRepository<MedicalEvent, Integer> {

//    @Query("SELECT m FROM MedicalEvent m LEFT JOIN FETCH m.medicalEventEventTypes WHERE m.eventID = :eventId")
//    Optional<MedicalEvent> findByIdWithEventTypes(@Param("eventId") Integer eventId);
//  // List<MedicalEvent> findByParentID(Integer parentId);


}
