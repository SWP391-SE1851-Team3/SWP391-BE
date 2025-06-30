package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.HealthConsentForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthConsentFormRepository extends JpaRepository<HealthConsentForm, Integer> {

    // Sử dụng query với student ID thay vì Student object
    @Query("SELECT h FROM HealthConsentForm h WHERE h.studentID = :studentId")
    List<HealthConsentForm> findByStudent(@Param("studentId") int studentId);

    @Query("SELECT h FROM HealthConsentForm h WHERE h.health_ScheduleID = :healthScheduleID")
    List<HealthConsentForm> findByHealth_ScheduleID(@Param("healthScheduleID") int healthScheduleID);

    @Query("SELECT h FROM HealthConsentForm h WHERE h.health_ScheduleID = :healthCheckScheduleID AND h.isAgreed = :isAgreed")
    List<HealthConsentForm> findByHealthCheckScheduleIDAndIsAgreed(@Param("healthCheckScheduleID") int healthCheckScheduleID, @Param("isAgreed") String isAgreed);

    @Query("SELECT h FROM HealthConsentForm h JOIN Student s ON h.studentID = s.StudentID WHERE s.className = :className AND h.health_ScheduleID = :healthCheckScheduleID")
    List<HealthConsentForm> findByStudentClassNameAndHealthCheckScheduleID(@Param("className") String className, @Param("healthCheckScheduleID") int healthCheckScheduleID);

    @Query("SELECT h FROM HealthConsentForm h JOIN Student s ON h.studentID = s.StudentID WHERE s.className = :className")
    List<HealthConsentForm> findByStudentClassName(@Param("className") String className);

}
