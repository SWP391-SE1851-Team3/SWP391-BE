package com.team_3.School_Medical_Management_System.InterFaceSerivce;

import com.team_3.School_Medical_Management_System.DTO.HealthCheckStudentSimplifiedDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentDTO;
import com.team_3.School_Medical_Management_System.Model.HealthCheck_Student;

import java.util.List;

public interface HealthCheckStudentServiceInterface {
    // Create health check results using DTO
    HealthCheck_Student createHealthCheckResults(HealthCheck_StudentDTO dto);

    // Get health check results for a student
    List<HealthCheck_Student> getHealthCheckResultsByStudent(int studentId);

    // Update health check results
    HealthCheck_Student updateHealthCheckResults(int id, HealthCheck_StudentDTO dto);

    // Delete health check results
    void deleteHealthCheckResults(int id);

    // Get all health check results
    List<HealthCheck_Student> getAllHealthCheckResults();

    // Get all health check results simplified
    List<HealthCheckStudentSimplifiedDTO> getAllHealthCheckResultsSimplified();

    // Get simplified health check results for a student
    List<HealthCheckStudentSimplifiedDTO> getSimplifiedHealthCheckResultsByStudent(int studentId);

    // Get simplified health check results by schedule
    List<HealthCheckStudentSimplifiedDTO> getSimplifiedHealthCheckResultsBySchedule(int health_ScheduleID);

    // Convert entity to simplified DTO
    HealthCheckStudentSimplifiedDTO convertEntityToSimplifiedDTO(HealthCheck_Student entity);

    // Get health check result by ID
    HealthCheck_Student getHealthCheckStudentById(int id);
}
