package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.HealthCheck_ScheduleDTO;
import com.team_3.School_Medical_Management_System.Model.HealthCheck_Schedule;
import com.team_3.School_Medical_Management_System.Service.HealthCheckScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/health-check-schedule")
public class HealthCheckScheduleController {

    @Autowired
    private HealthCheckScheduleService healthCheckScheduleService;

    // Create a new health check schedule
    @PostMapping
    public ResponseEntity<HealthCheck_Schedule> createHealthCheckSchedule(@RequestBody HealthCheck_ScheduleDTO healthCheckScheduleDTO) {
        HealthCheck_Schedule createdSchedule = healthCheckScheduleService.createHealthCheckSchedule(healthCheckScheduleDTO);
        return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
    }

    // Get all health check schedules
    @GetMapping
    public ResponseEntity<List<HealthCheck_Schedule>> getAllHealthCheckSchedules() {
        List<HealthCheck_Schedule> schedules = healthCheckScheduleService.getAllHealthCheckSchedules();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    // Get health check schedules by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<HealthCheck_Schedule>> getHealthCheckSchedulesByStatus(@PathVariable String status) {
        List<HealthCheck_Schedule> schedules = healthCheckScheduleService.getHealthCheckSchedulesByStatus(status);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    // Get a specific health check schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<HealthCheck_Schedule> getHealthCheckScheduleById(@PathVariable int id) {
        Optional<HealthCheck_Schedule> schedule = healthCheckScheduleService.getHealthCheckScheduleById(id);
        return schedule.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update health check schedule status
    @PutMapping("/{id}/status")
    public ResponseEntity<HealthCheck_Schedule> updateHealthCheckScheduleStatus(@PathVariable int id, @RequestParam String status) {
        HealthCheck_Schedule updatedSchedule = healthCheckScheduleService.updateHealthCheckScheduleStatus(id, status);
        if (updatedSchedule != null) {
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update health check schedule with any fields (partial update)
    @PutMapping("/{id}")
    public ResponseEntity<HealthCheck_Schedule> updateHealthCheckSchedule(@PathVariable int id, @RequestBody com.team_3.School_Medical_Management_System.DTO.HealthCheckScheduleUpdateDTO dto) {
        HealthCheck_Schedule updatedSchedule = healthCheckScheduleService.updateHealthCheckSchedule(id, dto);
        if (updatedSchedule != null) {
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
