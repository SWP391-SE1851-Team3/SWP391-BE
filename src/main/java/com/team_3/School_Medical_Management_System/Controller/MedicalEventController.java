package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.*;
import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicalEventTypeRepo;
import com.team_3.School_Medical_Management_System.Model.MedicalEventType;
import com.team_3.School_Medical_Management_System.Service.MedicalEventService;
import com.team_3.School_Medical_Management_System.Service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/medical-events")

@PreAuthorize("hasAuthority('ROLE_NURSE')")

public class MedicalEventController {

    @Autowired
    private MedicalEventService medicalEventService; // Service cho MedicalEvent
    @Autowired
    private MedicalEventTypeRepo medicalEventTypeRepo;
    @Autowired
    private StudentService studentService;

    @PostMapping("/emergency")
    @Operation(summary = "Tạo sự kiện y tế đột xuất và chi tiết liên quan")
    public ResponseEntity<MedicalEventDTO> createEmergencyEvent(@Valid
                                                                @RequestBody MedicalEventDTO dto) {
        MedicalEventDTO r = medicalEventService.createEmergencyEvent(dto);
        return ResponseEntity.ok(r);
    }


    @GetMapping("/className")
    public ResponseEntity<List<StudentsDTO>> getAllByClassName(@RequestParam List<String> className) {
        List<StudentsDTO> events = studentService.getAllStudentsByClassName(className);
        return ResponseEntity.ok(events);
    }



    @PreAuthorize("hasAuthority('ROLE_PARENT')")
    @Operation(summary = "Xem chi tiết sự cố y tế phần phụ huynh")
    @GetMapping("/getMedicalEventsDetailParent")
    public ResponseEntity<List<MedicalEventDetailParentDTO>> getMedicalEventsDetailParent(@RequestParam int parentId, @RequestParam int studentID) {
        List<MedicalEventDetailParentDTO> medicalEvents = new ArrayList<>();
        medicalEvents = medicalEventService.getMedicalDetailsParent(studentID, parentId);
        return ResponseEntity.ok(medicalEvents);
    }

    @PutMapping("/{eventId}")
    @Operation(summary = "Cập nhật sự kiện y tế")
    public ResponseEntity<MedicalEventUpdateDTO> updateMedicalEvent(
            @PathVariable int eventId,
            @RequestBody MedicalEventUpdateDTO dto
           ) {
        MedicalEventUpdateDTO r = medicalEventService.updateMedicalEvent(eventId, dto);
        return ResponseEntity.ok(r);
    }

    @Operation(summary = "Xem tất cả loại sự có y tế")
    @GetMapping("/getAllEventTypeName")
    public ResponseEntity<List<MedicalEventType>> getAllEventTypes() {
        List<MedicalEventType> eventTypes = medicalEventTypeRepo.findAll();

        return ResponseEntity.ok(eventTypes);
    }


    @GetMapping("/viewDetails/{eventId}")
    @Operation(summary = "Xem chi tiết sự kiện y tế")
    public ResponseEntity<MedicalEventDetailsDTO> getMedicalEventDetails(@PathVariable Integer eventId) {
        MedicalEventDetailsDTO response = medicalEventService.getMedicalEventDetails(eventId);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/deleteEvent/{eventId}")
    public ResponseEntity deleteMedicalEvent(@PathVariable Integer eventId) {
        medicalEventService.deleteMedicalEvent(eventId);
        return ResponseEntity.ok().build();
    }
}
