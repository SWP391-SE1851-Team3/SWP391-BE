package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.StudentMedicalEventDto;
import com.team_3.School_Medical_Management_System.Service.StudentMedicalEventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(name = "/api/student-medical-events")
public class StudentMedicalEventController {
    private final StudentMedicalEventService service;

    public StudentMedicalEventController(StudentMedicalEventService service) {
        this.service = service;
    }

    @GetMapping
    public List<StudentMedicalEventDto> getRecentMedicalEvents() {
        return service.getRecentMedicalEvents();
    }
}
