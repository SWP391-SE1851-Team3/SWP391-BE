package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.MedicalEventDTO;
import com.team_3.School_Medical_Management_System.Service.MedicalEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medical-events")

public class MedicalEventController {
@Autowired

    private MedicalEventService medicalEventService;

    public MedicalEventController(MedicalEventService medicalEventService) {
        this.medicalEventService = medicalEventService;
    }

    @PostMapping("/create/{nurseId}")
    public ResponseEntity<MedicalEventDTO> createMedicalEvent(@RequestBody MedicalEventDTO dto, @RequestParam Integer nurseId) {
        return ResponseEntity.ok( medicalEventService.createMedicalEvent(dto, nurseId));
    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getMedicalEventById(@PathVariable Integer id) {
//        return medicalEventService.getMedicalEventById(id);
//    }
//
    @PutMapping("/api/medical-events/{id}")
    public ResponseEntity<MedicalEventDTO> updateMedicalEvent(@RequestParam Integer nurseID, @RequestBody MedicalEventDTO dto) {
        return ResponseEntity.ok(medicalEventService);
    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteMedicalEvent(@PathVariable Integer id) {
//        return medicalEventService.deleteMedicalEvent(id);
//    }

}
