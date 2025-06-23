package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.HealthCheck_Student;
import com.team_3.School_Medical_Management_System.Model.HealthCheck_Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthCheckStudentRepository extends JpaRepository<HealthCheck_Student, Integer> {
    List<HealthCheck_Student> findByStudentID(int studentID);
    List<HealthCheck_Student> findByHealthCheckSchedule(HealthCheck_Schedule healthCheckSchedule);
}
