package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.HealthConsultationDTO;
import com.team_3.School_Medical_Management_System.Model.HealthConsultation;
import com.team_3.School_Medical_Management_System.Service.HealthConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/health-consultation")
public class HealthConsultationController {

    @Autowired
    private HealthConsultationService healthConsultationService;

    // Get all consultations
    @GetMapping
    public ResponseEntity<List<HealthConsultationDTO>> getAllConsultations() {
        List<HealthConsultation> consultations = healthConsultationService.getAllConsultations();
        List<HealthConsultationDTO> consultationDTOs = consultations.stream()
                .map(healthConsultationService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(consultationDTOs, HttpStatus.OK);
    }

    // Get consultations by status (pending/completed)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<HealthConsultationDTO>> getConsultationsByStatus(@PathVariable boolean status) {
        List<HealthConsultation> consultations = healthConsultationService.getConsultationsByStatus(status);
        List<HealthConsultationDTO> consultationDTOs = consultations.stream()
                .map(healthConsultationService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(consultationDTOs, HttpStatus.OK);
    }

    // Get consultations for a specific student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<HealthConsultationDTO>> getConsultationsByStudent(@PathVariable int studentId) {
        List<HealthConsultation> consultations = healthConsultationService.getConsultationsByStudent(studentId);
        List<HealthConsultationDTO> consultationDTOs = consultations.stream()
                .map(healthConsultationService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(consultationDTOs, HttpStatus.OK);
    }

    // Update consultation status
    @PutMapping("/{consultationId}")
    public ResponseEntity<HealthConsultationDTO> updateConsultationStatus(
            @PathVariable int consultationId,
            @RequestParam boolean status,
            @RequestParam(required = false) String notes) {

        HealthConsultation updatedConsultation = healthConsultationService.updateConsultationStatus(consultationId, status, notes);

        if (updatedConsultation != null) {
            return new ResponseEntity<>(healthConsultationService.convertToDTO(updatedConsultation), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
