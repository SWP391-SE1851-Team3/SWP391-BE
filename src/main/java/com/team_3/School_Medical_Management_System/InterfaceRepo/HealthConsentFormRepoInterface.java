package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.HealthConsentForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthConsentFormRepoInterface extends JpaRepository<HealthConsentForm,Integer> {
    @Modifying
    @Query("DELETE FROM HealthConsentForm h WHERE h.studentID = :studentId")
    void deleteByStudentId(@Param("studentId") int studentId);

    @Modifying
    @Query("DELETE FROM HealthConsentForm h WHERE h.parentID = :parentId")
    void deleteByParentID(@Param("parentId") int parentId);

    @Query("SELECT h FROM HealthConsentForm h WHERE h.studentID = :studentId")
    public List<HealthConsentForm> findByStudentID(@Param("studentId") int studentId);

   // public void deleteBy
}
