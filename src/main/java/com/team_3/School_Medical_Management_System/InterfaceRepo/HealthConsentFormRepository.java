package com.team_3.School_Medical_Management_System.InterfaceRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team_3.School_Medical_Management_System.Model.HealthConsentForm;

@Repository
public interface HealthConsentFormRepository extends JpaRepository<HealthConsentForm, Integer> {

    // Sử dụng query với student ID thay vì Student object
    List<HealthConsentForm> findByStudentID(int studentId);

    List<HealthConsentForm> findByHealthScheduleID(int healthScheduleID);

    List<HealthConsentForm> findByHealthScheduleIDAndIsAgreed(int healthScheduleID, String isAgreed);


//    @Query("SELECT h FROM HealthConsentForm h JOIN Student s ON h.studentID = s.StudentID WHERE s.className = :className AND h.healthScheduleID = :healthCheckScheduleID")
//    List<HealthConsentForm> findByStudentClassNameAndHealthCheckScheduleID(@Param("className") String className, @Param("healthCheckScheduleID") int healthCheckScheduleID);

//    @Query("SELECT h FROM HealthConsentForm h JOIN Student s ON h.studentID = s.StudentID WHERE s.className = :className")
//    List<HealthConsentForm> findByStudentClassName(@Param("className") String className);

    @Query("SELECT h FROM HealthConsentForm h JOIN Student s ON h.studentID = s.StudentID WHERE s.className = :className AND h.healthScheduleID = :healthScheduleID")
    List<HealthConsentForm> findByClassNameAndHealthScheduleID(@Param("className") String className, @Param("healthScheduleID") int healthScheduleID);


}
