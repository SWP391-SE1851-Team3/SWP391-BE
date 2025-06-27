package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.ConsentFormRequestDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthConsentFormDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HealthConsentFormService {

    @Autowired
    private HealthConsentFormRepository healthConsentFormRepository;

    @Autowired
    private StudentInterFace studentRepository;

    @Autowired
    private HealthCheckScheduleRepository healthCheckScheduleRepository;

    @Autowired
    private NotificationsParentRepository notificationsParentRepository;

    @Autowired
    private HealthCheckRepository healthCheckRepository;

    // Update consent form with parent's decision
    public HealthConsentForm updateConsentForm(int formId, String isAgreed, String notes) {
        Optional<HealthConsentForm> optionalForm = healthConsentFormRepository.findById(formId);

        if (optionalForm.isPresent()) {
            HealthConsentForm form = optionalForm.get();
            form.setIsAgreed(isAgreed);
            form.setNotes(notes);
            return healthConsentFormRepository.save(form);
        }

        return null;
    }

    // Get all consent forms for a specific health check schedule
    public List<HealthConsentForm> getConsentFormsBySchedule(int scheduleId) {
        return healthConsentFormRepository.findByHealth_ScheduleID(scheduleId);
    }

    // Get all agreed consent forms for a specific health check schedule
    public List<HealthConsentForm> getAgreedConsentFormsBySchedule(int scheduleId) {
        return healthConsentFormRepository.findByHealthCheckScheduleIDAndIsAgreed(scheduleId, "Approved");
    }

    // Get all consent forms by parentId
    public List<HealthConsentForm> getConsentFormsByParentId(Integer parentId) {
        List<Student> students = studentRepository.getStudentsByParentID(parentId);
        return students.stream()
                .flatMap(student -> healthConsentFormRepository.findByStudent(student.getStudentID()).stream())
                .collect(Collectors.toList());
    }

    // Get all consent forms
    public List<HealthConsentForm> getAllConsentForms() {
        return healthConsentFormRepository.findAll();
    }

    // Convert Entity to DTO
    public HealthConsentFormDTO convertToDTO(HealthConsentForm form) {
        HealthConsentFormDTO dto = new HealthConsentFormDTO();
        dto.setFormID(form.getFormID());
        dto.setStudentID(form.getStudentID()); // form.getStudent() trả về int studentID
        dto.setHealthScheduleID(form.getHealth_ScheduleID()); // form.getHealthCheckSchedule() trả về int healthScheduleID
        dto.setIsAgreed(form.getIsAgreed());
        dto.setNotes(form.getNotes());
        dto.setSend_date(form.getSend_date());
        dto.setExpire_date(form.getExpire_date());

        // Fetch student name and class name from database using studentID
        try {
            Optional<Student> studentOpt = studentRepository.findById(form.getStudentID());
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                dto.setStudentName(student.getFullName());
                dto.setClassName(student.getClassName());
                dto.setParentID(form.getParentID()); // Set parentID from form
            }
        } catch (Exception e) {
            // Log error but continue processing
            System.err.println("Error fetching student info for ID " + form.getStudentID() + ": " + e.getMessage());
        }

        // Fetch health schedule name from database using healthScheduleID
        try {
            Optional<HealthCheck_Schedule> scheduleOpt = healthCheckScheduleRepository.findById(form.getHealth_ScheduleID());
            if (scheduleOpt.isPresent()) {
                HealthCheck_Schedule schedule = scheduleOpt.get();
                dto.setHealthScheduleName(schedule.getName());
            }
        } catch (Exception e) {
            // Log error but continue processing
            System.err.println("Error fetching schedule info for ID " + form.getHealth_ScheduleID() + ": " + e.getMessage());
        }

        return dto;
    }

    public void createConsentFormsForClass(ConsentFormRequestDTO request) {
        List<Student> students = studentRepository.findByClassName(request.getClassName());
        HealthCheck_Schedule schedule = healthCheckScheduleRepository.findById(request.getHealthScheduleId())
                .orElseThrow(() -> new RuntimeException("HealthCheck_Schedule not found with id: " + request.getHealthScheduleId()));

        for (Student student : students) {
            // Create HealthConsentForm
            HealthConsentForm consentForm = new HealthConsentForm();
            consentForm.setStudentID(student.getStudentID());
            consentForm.setParentID(student.getParent().getParentID());
            consentForm.setHealth_ScheduleID(schedule.getHealth_ScheduleID()); // Set scheduleID thay vì Schedule object
            consentForm.setSend_date(new Date());
            consentForm.setExpire_date(request.getExpireDate());
            consentForm.setIsAgreed(request.getIsAgreed());
            consentForm.setNotes(request.getNotes());
            consentForm.setCreatedByNurseID(request.getCreatedByNurseId());
            consentForm.setUpdatedByNurseID(request.getUpdatedByNurseID());
            HealthConsentForm savedConsentForm = healthConsentFormRepository.save(consentForm);

            // Create corresponding HealthCheck entry
            HealthCheck healthCheck = new HealthCheck();
            healthCheck.setFormID(savedConsentForm.getFormID());
            healthCheckRepository.save(healthCheck);

            // Create notification for parent
            if (student.getParent() != null) {
                NotificationsParent notification = new NotificationsParent();
                notification.setParent(student.getParent());
                notification.setTitle("Yêu cầu đồng ý kiểm tra sức khỏe cho học sinh " + student.getFullName());
                notification.setContent("Một đợt khám sức khỏe mới đã được lên lịch. Vui lòng xem và xác nhận đồng ý cho con em của bạn.");
                notification.setCreateAt(LocalDateTime.now());
                notification.setStatus(false);
                notificationsParentRepository.save(notification);
            }
        }
    }

    public List<HealthConsentForm> getConsentFormsByClass(String className) {
        return healthConsentFormRepository.findByStudentClassName(className);
    }

    public List<HealthConsentForm> getConsentFormsByClassAndSchedule(String className, Integer scheduleId) {
        return healthConsentFormRepository.findByStudentClassNameAndHealthCheckScheduleID(className, scheduleId);
    }

    public List<HealthConsentForm> getAcceptedConsentForms(Integer scheduleId) {
        return healthConsentFormRepository.findByHealthCheckScheduleIDAndIsAgreed(scheduleId, "true");
    }

    public List<HealthConsentForm> getRejectedConsentForms(Integer scheduleId) {
        return healthConsentFormRepository.findByHealthCheckScheduleIDAndIsAgreed(scheduleId, "false");
    }


}
