package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.MedicalEventDTO;
import com.team_3.School_Medical_Management_System.Service.MedicalEventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-events")

public class MedicalEventController {
@Autowired

    private MedicalEventService medicalEventService;

    public MedicalEventController(MedicalEventService medicalEventService) {
        this.medicalEventService = medicalEventService;
    }

    @PostMapping("/create")
    public ResponseEntity<MedicalEventDTO> createMedicalEvent(@RequestBody @Valid  MedicalEventDTO dto, @RequestParam Integer nurseId) {
        return ResponseEntity.ok( medicalEventService.createMedicalSudden(dto, nurseId));
    }

    @GetMapping("/{parentId}")
    public ResponseEntity<List<MedicalEventDTO>> getAllEventStudentById(@PathVariable Integer parentId) {


        return ResponseEntity.ok(medicalEventService.getMedicalEventsByParentId(parentId));
    }

    @PutMapping("/update/{eventId}")
    public ResponseEntity<MedicalEventDTO> updateMedicalEvent(
            @PathVariable Integer eventId,//({eventId}) dành cho @PathVariable
            @Valid @RequestBody MedicalEventDTO dto,
            @RequestParam Integer nurseId) {
        return ResponseEntity.ok(medicalEventService.updateEventDTO(eventId, dto, nurseId));
    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteMedicalEvent(@PathVariable Integer id) {
//        return medicalEventService.deleteMedicalEvent(id);
//    }

}
