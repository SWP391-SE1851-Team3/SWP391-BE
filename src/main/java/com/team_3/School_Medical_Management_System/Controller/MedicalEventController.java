package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.*;
import com.team_3.School_Medical_Management_System.Service.MedicalEventService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/medical-events")

public class MedicalEventController {

    @Autowired
    private MedicalEventService medicalEventService; // Service cho MedicalEvent

    @PostMapping("/emergency")
    @Operation(summary = "Tạo sự kiện y tế đột xuất và chi tiết liên quan")
    public ResponseEntity<MedicalEventDTO> createEmergencyEvent(
            @RequestBody MedicalEventDTO dto,
            @RequestParam int studentId,
            @RequestParam String note,
            @RequestParam String result,
            @RequestParam String processingStatus ,
            @RequestParam Integer eventTypeId) {
        MedicalEventDTO r = medicalEventService.createEmergencyEvent(dto, studentId, note, result, processingStatus,eventTypeId);
        return ResponseEntity.ok(r);
    }

    @PutMapping("/{eventId}")
    @Operation(summary = "Cập nhật sự kiện y tế")
    public ResponseEntity<MedicalEventUpdateDTO> updateMedicalEvent(
            @PathVariable int eventId,
            @RequestBody MedicalEventUpdateDTO dto) {
        MedicalEventUpdateDTO r = medicalEventService.updateMedicalEvent(eventId , dto);
        return ResponseEntity.ok(r);
    }

    @GetMapping("/parent/{parentId}/emergency")
    @Operation(summary = "Xem các sự kiện y tế đột xuất của con")
    public ResponseEntity<List<MedicalEventDTO>> getEmergencyEventsByParent(@PathVariable int parentId,@PathVariable int studentId) {
        List<MedicalEventDTO> events = medicalEventService.getAllMedicalEventsByParent(parentId, studentId);
        return ResponseEntity.ok(events);
    }

}
