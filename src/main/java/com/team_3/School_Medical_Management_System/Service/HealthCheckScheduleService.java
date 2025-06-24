package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.HealthCheck_ScheduleDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.HealthCheckScheduleRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.HealthConsentFormRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.NotificationsParentRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.StudentRepository;
import com.team_3.School_Medical_Management_System.Model.HealthCheck_Schedule;
import com.team_3.School_Medical_Management_System.Model.HealthConsentForm;
import com.team_3.School_Medical_Management_System.Model.NotificationsParent;
import com.team_3.School_Medical_Management_System.Model.Student;
import com.team_3.School_Medical_Management_System.Repositories.*;
import com.team_3.School_Medical_Management_System.InterfaceRepo.HealthCheckScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    // Create a new health check schedule
    public HealthCheck_Schedule createHealthCheckSchedule(HealthCheck_ScheduleDTO healthCheckScheduleDTO) {
        HealthCheck_Schedule healthCheckSchedule = new HealthCheck_Schedule();
        healthCheckSchedule.setName(healthCheckScheduleDTO.getName());
        healthCheckSchedule.setSchedule_Date(healthCheckScheduleDTO.getSchedule_Date());
        healthCheckSchedule.setLocation(healthCheckScheduleDTO.getLocation());
        healthCheckSchedule.setNotes(healthCheckScheduleDTO.getNotes());
        healthCheckSchedule.setStatus(healthCheckScheduleDTO.getStatus());

        HealthCheck_Schedule savedSchedule = healthCheckScheduleRepository.save(healthCheckSchedule);

        // Automatically create consent forms for all students
        createConsentFormsForAllStudents(savedSchedule);

        return savedSchedule;
    }

    // Create consent forms for all students and send notifications to parents
    private void createConsentFormsForAllStudents(HealthCheck_Schedule healthCheckSchedule) {
        List<Student> allStudents = studentRepository.findAll();

        for (Student student : allStudents) {
            // Create a consent form for each student
            HealthConsentForm consentForm = new HealthConsentForm();
            consentForm.setStudent(student);
            consentForm.setHealthCheckSchedule(healthCheckSchedule);
            consentForm.setIsAgreed(null); // Not decided yet
            healthConsentFormRepository.save(consentForm);

            // Send notification to parent
            if (student.getParent() != null) {
                NotificationsParent notification = new NotificationsParent();
                notification.setParent(student.getParent());
                notification.setTitle("Yêu cầu đồng ý kiểm tra sức khỏe");
                notification.setContent("Đợt kiểm tra sức khỏe mới '" + healthCheckSchedule.getName() +
                        "' vào ngày " + healthCheckSchedule.getSchedule_Date() +
                        " tại " + healthCheckSchedule.getLocation() +
                        ". Vui lòng xác nhận sự đồng ý cho con bạn tham gia.");
                notification.setCreateAt(LocalDateTime.now());
                notificationParentRepository.save(notification);
            }
        }
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
            return healthCheckScheduleRepository.save(schedule);
        }
        return null;
    }
}
