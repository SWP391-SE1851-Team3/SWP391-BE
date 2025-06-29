package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.HealthCheck_ScheduleDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.HealthCheckScheduleRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.HealthConsentFormRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.NotificationsParentRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.StudentRepository;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.*;
import com.team_3.School_Medical_Management_System.InterfaceRepo.HealthCheckScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HealthCheckScheduleService {

    @Autowired
    private HealthCheckScheduleRepository healthCheckScheduleRepository;

    @Autowired
    private HealthConsentFormRepository healthConsentFormRepository;

    @Autowired
    private NotificationsParentRepository notificationParentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SchoolNurseService schoolNurseService;

    // Create a new health check schedule
    public HealthCheck_Schedule createHealthCheckSchedule(HealthCheck_ScheduleDTO healthCheckScheduleDTO) {
        HealthCheck_Schedule healthCheckSchedule = new HealthCheck_Schedule();
        healthCheckSchedule.setName(healthCheckScheduleDTO.getName());
        healthCheckSchedule.setSchedule_Date(healthCheckScheduleDTO.getSchedule_Date());
        healthCheckSchedule.setLocation(healthCheckScheduleDTO.getLocation());
        healthCheckSchedule.setNotes(healthCheckScheduleDTO.getNotes());
        healthCheckSchedule.setStatus(healthCheckScheduleDTO.getStatus());

        // Set creation information
        healthCheckSchedule.setCreate_at(new Date());
        healthCheckSchedule.setCreatedByNurseID(healthCheckScheduleDTO.getCreatedByNurseID());
        healthCheckSchedule.setCreatedByNurseName(healthCheckScheduleDTO.getCreatedByNurseName());

        // Set update information if provided
        if (healthCheckScheduleDTO.getUpdatedByNurseID() != null) {
            healthCheckSchedule.setUpdate_at(new Date());
            healthCheckSchedule.setUpdatedByNurseID(healthCheckScheduleDTO.getUpdatedByNurseID());
            healthCheckSchedule.setUpdatedByNurseName(healthCheckScheduleDTO.getUpdatedByNurseName());
        }

        HealthCheck_Schedule savedSchedule = healthCheckScheduleRepository.save(healthCheckSchedule);

        return savedSchedule;
    }


    // Get all health check schedules
    public List<HealthCheck_Schedule> getAllHealthCheckSchedules() {
        return healthCheckScheduleRepository.findAll();
    }

    // Get health check schedules by status
    public List<HealthCheck_Schedule> getHealthCheckSchedulesByStatus(String status) {
        return healthCheckScheduleRepository.findByStatus(status);
    }

    // Get a specific health check schedule by ID
    public Optional<HealthCheck_Schedule> getHealthCheckScheduleById(int id) {
        return healthCheckScheduleRepository.findById(id);
    }

    // Update health check schedule status
    public HealthCheck_Schedule updateHealthCheckScheduleStatus(int id, String status) {
        Optional<HealthCheck_Schedule> optionalSchedule = healthCheckScheduleRepository.findById(id);
        if (optionalSchedule.isPresent()) {
            HealthCheck_Schedule schedule = optionalSchedule.get();
            schedule.setStatus(status);
            return healthCheckScheduleRepository.save(schedule);
        }
        return null;
    }

    // Update health check schedule with partial fields
    public HealthCheck_Schedule updateHealthCheckSchedule(int id, com.team_3.School_Medical_Management_System.DTO.HealthCheckScheduleUpdateDTO dto) {
        Optional<HealthCheck_Schedule> optionalSchedule = healthCheckScheduleRepository.findById(id);
        if (optionalSchedule.isPresent()) {
            HealthCheck_Schedule schedule = optionalSchedule.get();
            if (dto.getName() != null) schedule.setName(dto.getName());
            if (dto.getLocation() != null) schedule.setLocation(dto.getLocation());
            if (dto.getNotes() != null) schedule.setNotes(dto.getNotes());
            if (dto.getStatus() != null) schedule.setStatus(dto.getStatus());
            if (dto.getSchedule_Date() != null) schedule.setSchedule_Date(dto.getSchedule_Date());

            // Update information about who is updating
            if (dto.getUpdatedByNurseID() != null) schedule.setUpdatedByNurseID(dto.getUpdatedByNurseID());
            if (dto.getUpdatedByNurseName() != null) schedule.setUpdatedByNurseName(dto.getUpdatedByNurseName());

            return healthCheckScheduleRepository.save(schedule);
        }
        return null;
    }

    // Update health check schedule
    public HealthCheck_Schedule updateHealthCheckSchedule(int id, HealthCheck_ScheduleDTO dto) {
        Optional<HealthCheck_Schedule> optionalSchedule = healthCheckScheduleRepository.findById(id);
        if (optionalSchedule.isPresent()) {
            HealthCheck_Schedule schedule = optionalSchedule.get();

            // Update basic information
            schedule.setName(dto.getName());
            schedule.setSchedule_Date(dto.getSchedule_Date());
            schedule.setLocation(dto.getLocation());
            schedule.setNotes(dto.getNotes());
            schedule.setStatus(dto.getStatus());
            // Removed nurseID setting

            // Set update information only
            schedule.setUpdate_at(new Date());
            schedule.setUpdatedByNurseID(dto.getUpdatedByNurseID());
            schedule.setUpdatedByNurseName(dto.getUpdatedByNurseName());

            // IMPORTANT: Do not change the createdByNurseID and createdByNurseName values
            // The original creation information is maintained

            return healthCheckScheduleRepository.save(schedule);
        }
        return null;
    }

    // Update health check schedule with UpdateFullDTO to prevent creation fields from being modified
    public HealthCheck_Schedule updateHealthCheckScheduleWithUpdateDTO(int id, com.team_3.School_Medical_Management_System.DTO.HealthCheckScheduleUpdateFullDTO dto) {
        Optional<HealthCheck_Schedule> optionalSchedule = healthCheckScheduleRepository.findById(id);
        if (optionalSchedule.isPresent()) {
            HealthCheck_Schedule schedule = optionalSchedule.get();
            // Update basic information
            schedule.setName(dto.getName());
            schedule.setSchedule_Date(dto.getSchedule_Date());
            schedule.setLocation(dto.getLocation());
            schedule.setNotes(dto.getNotes());
            schedule.setStatus(dto.getStatus());
            // Removed nurseID setting

            // Set update information only
            schedule.setUpdate_at(new Date());
            schedule.setUpdatedByNurseID(dto.getUpdatedByNurseID());
            schedule.setUpdatedByNurseName(dto.getUpdatedByNurseName());

            // NOTE: Creation fields (createdByNurseID, createdByNurseName, create_at) are never modified
            // since they don't exist in the DTO

            return healthCheckScheduleRepository.save(schedule);
        }
        return null;
    }

    // Update all health check schedules with nurse names
    public List<HealthCheck_Schedule> getAllHealthCheckSchedulesWithNurseNames() {
        List<HealthCheck_Schedule> schedules = healthCheckScheduleRepository.findAll();

        // Populate nurse names for all schedules
        for (HealthCheck_Schedule schedule : schedules) {
            // Get creator nurse name if available
            if (schedule.getCreatedByNurseID() != null && schedule.getCreatedByNurseID() > 0
                && (schedule.getCreatedByNurseName() == null || schedule.getCreatedByNurseName().isEmpty())) {
                try {
                    SchoolNurse nurse = schoolNurseService.GetSchoolNursesById(schedule.getCreatedByNurseID());
                    if (nurse != null) {
                        schedule.setCreatedByNurseName(nurse.getFullName());
                    }
                } catch (Exception e) {
                    // Continue even if one nurse lookup fails
                }
            }

            // Get updater nurse name if available
            if (schedule.getUpdatedByNurseID() != null && schedule.getUpdatedByNurseID() > 0
                && (schedule.getUpdatedByNurseName() == null || schedule.getUpdatedByNurseName().isEmpty())) {
                try {
                    SchoolNurse nurse = schoolNurseService.GetSchoolNursesById(schedule.getUpdatedByNurseID());
                    if (nurse != null) {
                        schedule.setUpdatedByNurseName(nurse.getFullName());
                    }
                } catch (Exception e) {
                    // Continue even if one nurse lookup fails
                }
            }
        }

        return schedules;
    }

    // Update schedule without sending notifications (used for updating nurse names)
    public HealthCheck_Schedule updateScheduleWithoutNotifications(HealthCheck_Schedule schedule) {
        return healthCheckScheduleRepository.save(schedule);
    }
}


