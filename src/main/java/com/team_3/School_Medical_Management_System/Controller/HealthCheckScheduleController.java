package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.HealthCheck_ScheduleDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheckScheduleUpdateDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheckScheduleUpdateFullDTO;
import com.team_3.School_Medical_Management_System.Model.HealthCheck_Schedule;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import com.team_3.School_Medical_Management_System.Service.HealthCheckScheduleService;
import com.team_3.School_Medical_Management_System.Service.SchoolNurseService;
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

    @Autowired
    private SchoolNurseService schoolNurseService;

    // Create a new health check schedule with nurse information
    @PostMapping
    public ResponseEntity<HealthCheck_Schedule> createHealthCheckSchedule(@RequestBody HealthCheck_ScheduleDTO healthCheckScheduleDTO) {
        // If we have creator nurse ID, get the nurse name
        if (healthCheckScheduleDTO.getCreatedByNurseID() != null && healthCheckScheduleDTO.getCreatedByNurseID() > 0) {
            // Get the nurse by ID and set the name if found
            SchoolNurse nurse = schoolNurseService.GetSchoolNursesById(healthCheckScheduleDTO.getCreatedByNurseID());
            if (nurse != null) {
                // Always set creator nurse name from nurse ID
                healthCheckScheduleDTO.setCreatedByNurseName(nurse.getFullName());
            }
        }

        // If we have updater nurse ID, get the nurse name
        if (healthCheckScheduleDTO.getUpdatedByNurseID() != null && healthCheckScheduleDTO.getUpdatedByNurseID() > 0) {
            SchoolNurse nurse = schoolNurseService.GetSchoolNursesById(healthCheckScheduleDTO.getUpdatedByNurseID());
            if (nurse != null) {
                // Always set updater nurse name from nurse ID
                healthCheckScheduleDTO.setUpdatedByNurseName(nurse.getFullName());
            }
        }

        HealthCheck_Schedule createdSchedule = healthCheckScheduleService.createHealthCheckSchedule(healthCheckScheduleDTO);


        if (createdSchedule.getUpdatedByNurseID() != null &&
            (createdSchedule.getUpdatedByNurseName() == null || createdSchedule.getUpdatedByNurseName().isEmpty())) {
            SchoolNurse nurse = schoolNurseService.GetSchoolNursesById(createdSchedule.getUpdatedByNurseID());
            if (nurse != null) {
                createdSchedule.setUpdatedByNurseName(nurse.getFullName());
                healthCheckScheduleService.updateScheduleWithoutNotifications(createdSchedule);
            }
        }

        return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
    }

    // Get all health check schedules
    @GetMapping
    public ResponseEntity<List<HealthCheck_Schedule>> getAllHealthCheckSchedules() {
        List<HealthCheck_Schedule> schedules = healthCheckScheduleService.getAllHealthCheckSchedulesWithNurseNames();
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
        Optional<HealthCheck_Schedule> optionalSchedule = healthCheckScheduleService.getHealthCheckScheduleById(id);

        if (optionalSchedule.isPresent()) {
            HealthCheck_Schedule schedule = optionalSchedule.get();

            // Get creator nurse name if needed
            if (schedule.getCreatedByNurseID() != null && schedule.getCreatedByNurseID() > 0
                && (schedule.getCreatedByNurseName() == null || schedule.getCreatedByNurseName().isEmpty())) {
                SchoolNurse nurse = schoolNurseService.GetSchoolNursesById(schedule.getCreatedByNurseID());
                if (nurse != null) {
                    schedule.setCreatedByNurseName(nurse.getFullName());
                    // Save the updated name
                    healthCheckScheduleService.updateScheduleWithoutNotifications(schedule);
                }
            }

            // Get updater nurse name if needed
            if (schedule.getUpdatedByNurseID() != null && schedule.getUpdatedByNurseID() > 0
                && (schedule.getUpdatedByNurseName() == null || schedule.getUpdatedByNurseName().isEmpty())) {
                SchoolNurse nurse = schoolNurseService.GetSchoolNursesById(schedule.getUpdatedByNurseID());
                if (nurse != null) {
                    schedule.setUpdatedByNurseName(nurse.getFullName());
                    // Save the updated name
                    healthCheckScheduleService.updateScheduleWithoutNotifications(schedule);
                }
            }

            return new ResponseEntity<>(schedule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update health check schedule
    @PutMapping("/{id}")
    public ResponseEntity<HealthCheck_Schedule> updateHealthCheckSchedule(
            @PathVariable int id,
            @RequestBody HealthCheckScheduleUpdateFullDTO dto) {

        // Handle updater nurse information
        if (dto.getUpdatedByNurseID() != null && dto.getUpdatedByNurseID() > 0) {
            SchoolNurse nurse = schoolNurseService.GetSchoolNursesById(dto.getUpdatedByNurseID());
            if (nurse != null) {
                // Set updater nurse name if not already set
                if (dto.getUpdatedByNurseName() == null || dto.getUpdatedByNurseName().isEmpty()) {
                    dto.setUpdatedByNurseName(nurse.getFullName());
                }
            }
        }

        HealthCheck_Schedule updatedSchedule = healthCheckScheduleService.updateHealthCheckScheduleWithUpdateDTO(id, dto);
        if (updatedSchedule != null) {
            return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
