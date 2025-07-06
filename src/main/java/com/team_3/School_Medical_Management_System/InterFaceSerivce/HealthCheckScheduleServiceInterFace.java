package com.team_3.School_Medical_Management_System.InterFaceSerivce;

import com.team_3.School_Medical_Management_System.DTO.HealthCheck_ScheduleDTO;
import com.team_3.School_Medical_Management_System.Model.HealthCheck_Schedule;

import java.util.List;
import java.util.Optional;

public interface HealthCheckScheduleServiceInterFace {

    // Create a new health check schedule
    HealthCheck_Schedule createHealthCheckSchedule(HealthCheck_ScheduleDTO healthCheckScheduleDTO);

    // Get all health check schedules
    List<HealthCheck_Schedule> getAllHealthCheckSchedules();

    // Get health check schedules by status
    List<HealthCheck_Schedule> getHealthCheckSchedulesByStatus(String status);

    // Get a specific health check schedule by ID
    Optional<HealthCheck_Schedule> getHealthCheckScheduleById(int id);

    // Update health check schedule status
    HealthCheck_Schedule updateHealthCheckScheduleStatus(int id, String status);
}
