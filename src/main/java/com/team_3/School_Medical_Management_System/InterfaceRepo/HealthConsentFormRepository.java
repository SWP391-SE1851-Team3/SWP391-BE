package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.HealthConsentForm;
import com.team_3.School_Medical_Management_System.Model.HealthCheck_Schedule;
import com.team_3.School_Medical_Management_System.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthConsentFormRepository extends JpaRepository<HealthConsentForm, Integer> {
    List<HealthConsentForm> findByStudent(Student student);
    List<HealthConsentForm> findByHealthCheckSchedule(HealthCheck_Schedule healthCheckSchedule);
    List<HealthConsentForm> findByHealthCheckScheduleAndIsAgreed(HealthCheck_Schedule healthCheckSchedule, Boolean isAgreed);
    List<HealthConsentForm> findByStudentAndIsProcessed(Student student, Boolean isProcessed);
}
