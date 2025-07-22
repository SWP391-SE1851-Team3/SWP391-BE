package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalEvent;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalEventRepo extends JpaRepository<MedicalEvent, Integer> {



    @Modifying
    @Query("DELETE FROM MedicalEventMedicalSupply mems WHERE mems.medicalEvent.eventID = :eventId")
    void deleteMedicalEventMedicalSuppliesByEventId(@Param("eventId") Integer eventId);
}
