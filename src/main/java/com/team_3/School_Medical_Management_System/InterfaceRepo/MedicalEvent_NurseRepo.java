package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalEvent_Nurse;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MedicalEvent_NurseRepo extends JpaRepository<MedicalEvent_Nurse, Integer> {
    @Modifying
    @Query("DELETE FROM MedicalEvent_Nurse men WHERE men.eventID = :eventId")
    void deleteByMedicalEvent_EventID(@Param("eventId") Integer eventId);
   // void deleteByMedicalEvent_EventID(Integer eventId);
//    Optional<MedicalEvent_Nurse> findByMedicalEvent_EventIDAndSchoolNurse_nurseID(int eventId, int nurseId);
}
