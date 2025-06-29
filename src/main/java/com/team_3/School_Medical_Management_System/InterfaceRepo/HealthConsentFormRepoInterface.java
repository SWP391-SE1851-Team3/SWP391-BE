package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.HealthConsentForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthConsentFormRepoInterface extends JpaRepository<HealthConsentForm,Integer> {
    @Modifying
    @Query("DELETE FROM HealthConsentForm h WHERE h.student.StudentID = :studentId")
    void deleteByStudentId(@Param("studentId") int studentId);

   // public void deleteBy
}
