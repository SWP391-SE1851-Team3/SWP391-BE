package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.HealthCheck_Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface HealthCheckStudentRepository extends JpaRepository<HealthCheck_Student, Integer> {
    @Query("SELECT h FROM HealthCheck_Student h WHERE h.studentID = :studentId")
    List<HealthCheck_Student> findByStudent_StudentID(@Param("studentId") int studentId);

    @Query("SELECT MAX(h.checkID) FROM HealthCheck_Student h WHERE h.studentID = :studentId")
    Integer findMaxCheckIdByStudentId(@Param("studentId") int studentId);

    @Query("SELECT h FROM HealthCheck_Student h WHERE h.health_ScheduleID = :health_ScheduleID")
    List<HealthCheck_Student> findByHealth_ScheduleID(@Param("health_ScheduleID") int health_ScheduleID);
}
