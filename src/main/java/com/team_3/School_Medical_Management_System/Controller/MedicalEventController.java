package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.*;
import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicalEventTypeRepo;
import com.team_3.School_Medical_Management_System.Model.MedicalEventType;
import com.team_3.School_Medical_Management_System.Model.Student;
import com.team_3.School_Medical_Management_System.Service.MedicalEventService;
import com.team_3.School_Medical_Management_System.Service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/medical-events")

public class MedicalEventController {

    @Autowired
    private MedicalEventService medicalEventService; // Service cho MedicalEvent
    @Autowired
    private MedicalEventTypeRepo medicalEventTypeRepo;
@Autowired
private StudentService studentService;
    @PostMapping("/emergency")
    @Operation(summary = "Tạo sự kiện y tế đột xuất và chi tiết liên quan")
    public ResponseEntity<MedicalEventDTO> createEmergencyEvent(
            @RequestBody MedicalEventDTO dto,
            @RequestParam int studentId,
            @RequestParam String note,
            @RequestParam String result,
            @RequestParam String processingStatus,
            @RequestParam Integer eventTypeId
    ) {
        MedicalEventDTO r = medicalEventService.createEmergencyEvent(dto, studentId, note, result, processingStatus, eventTypeId);
        return ResponseEntity.ok(r);
    }


    @GetMapping("/{className}")
    public ResponseEntity<List<StudentsDTO>> getAllByClassName(@PathVariable String className) {
        List<StudentsDTO> events = studentService.getAllStudentsByClassName(className);
        return ResponseEntity.ok(events);
    }


    @PutMapping("/{eventId}")
    @Operation(summary = "Cập nhật sự kiện y tế")
    public ResponseEntity<MedicalEventUpdateDTO> updateMedicalEvent(
            @PathVariable int eventId,
            @RequestBody MedicalEventUpdateDTO dto,
            @RequestParam int eventTypeId){
        MedicalEventUpdateDTO r = medicalEventService.updateMedicalEvent(eventId, dto,eventTypeId);
        return ResponseEntity.ok(r);
    }


    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllEventTypes() {
        List<MedicalEventType> eventTypes = medicalEventTypeRepo.findAll();
        List<String> typeNames = new ArrayList<>();
        for (MedicalEventType eventType : eventTypes) {
            typeNames.add(eventType.getTypeName());
        }
        return ResponseEntity.ok(typeNames);
    }

}
