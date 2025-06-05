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
    @PostMapping("/api/medicalEvent")
    public ResponseEntity<MedicalEventDTO> createMedicalEvent(@RequestBody MedicalEventDTO medicalEventDTO, @RequestParam int nusreID) {
        // Logic to create a medical event

        return ResponseEntity.ok().build();
    }
}
