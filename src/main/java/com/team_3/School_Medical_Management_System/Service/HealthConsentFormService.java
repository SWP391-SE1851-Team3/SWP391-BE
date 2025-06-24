package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.HealthConsentFormDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.HealthCheckScheduleRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.HealthConsentFormRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.StudentInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.StudentRepository;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    // Update consent form with parent's decision
    public HealthConsentForm updateConsentForm(int formId, boolean isAgreed, String notes) {
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
        Optional<HealthCheck_Schedule> schedule = healthCheckScheduleRepository.findById(scheduleId);

        if (schedule.isPresent()) {
            return healthConsentFormRepository.findByHealthCheckSchedule(schedule.get());
        }

        return List.of();
    }

    // Get all agreed consent forms for a specific health check schedule
    public List<HealthConsentForm> getAgreedConsentFormsBySchedule(int scheduleId) {
        Optional<HealthCheck_Schedule> schedule = healthCheckScheduleRepository.findById(scheduleId);

        if (schedule.isPresent()) {
            return healthConsentFormRepository.findByHealthCheckScheduleAndIsAgreed(schedule.get(), true);
        }

        return List.of();
    }

    // Get all consent forms by parentId
    public List<HealthConsentForm> getConsentFormsByParentId(Integer parentId) {
        List<Student> students = studentRepository.getStudentsByParentID(parentId);
        return students.stream()
                .flatMap(student -> healthConsentFormRepository.findByStudent(student).stream())
                .collect(Collectors.toList());
    }

    // Convert Entity to DTO
    public HealthConsentFormDTO convertToDTO(HealthConsentForm form) {
        HealthConsentFormDTO dto = new HealthConsentFormDTO();
        dto.setFormID(form.getFormID());
        dto.setStudentID(form.getStudent().getStudentID());
        dto.setStudentName(form.getStudent().getFullName());
        dto.setHealthScheduleID(form.getHealthCheckSchedule().getHealth_ScheduleID());
        dto.setHealthScheduleName(form.getHealthCheckSchedule().getName());
        dto.setIsAgreed(form.getIsAgreed());
        dto.setNotes(form.getNotes());
        return dto;
    }
}
