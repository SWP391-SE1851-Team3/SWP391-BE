package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.HealthConsultationDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthConsultationUpdateDTO;
import com.team_3.School_Medical_Management_System.Model.HealthConsultation;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import com.team_3.School_Medical_Management_System.Service.HealthConsultationService;
import com.team_3.School_Medical_Management_System.Service.SchoolNurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/health-consultation")
public class HealthConsultationController {

    @Autowired
    private HealthConsultationService healthConsultationService;

    @Autowired
    private SchoolNurseService schoolNurseService;

    // Get all consultations
    @GetMapping("/all")
    public ResponseEntity<List<HealthConsultationDTO>> getAllConsultations() {
        List<HealthConsultation> consultations = healthConsultationService.getAllConsultations();
        List<HealthConsultationDTO> consultationDTOs = consultations.stream()
                .map(healthConsultationService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(consultationDTOs, HttpStatus.OK);
    }

    // Get consultations by status (pending/completed)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<HealthConsultationDTO>> getConsultationsByStatus(@PathVariable String status) {
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

    // Create a new consultation
    @PostMapping
    public ResponseEntity<HealthConsultationDTO> createConsultation(@RequestBody HealthConsultationDTO consultationDTO) {
        // Handle creator nurse info
        if (consultationDTO.getCreatedByNurseID() != null && consultationDTO.getCreatedByNurseID() > 0) {
            SchoolNurse creatorNurse = schoolNurseService.GetSchoolNursesById(consultationDTO.getCreatedByNurseID());
            if (creatorNurse != null) {
                consultationDTO.setCreatedByNurseName(creatorNurse.getFullName());
            }
        }

        // Handle updater nurse info. If not provided, it defaults to creator.
        if (consultationDTO.getUpdatedByNurseID() != null && consultationDTO.getUpdatedByNurseID() > 0) {
            SchoolNurse updaterNurse = schoolNurseService.GetSchoolNursesById(consultationDTO.getUpdatedByNurseID());
            if (updaterNurse != null) {
                consultationDTO.setUpdatedByNurseName(updaterNurse.getFullName());
            }
        } else {
            // If no updater is specified, the creator is the initial updater.
            consultationDTO.setUpdatedByNurseID(consultationDTO.getCreatedByNurseID());
            consultationDTO.setUpdatedByNurseName(consultationDTO.getCreatedByNurseName());
        }

        HealthConsultation createdConsultation = healthConsultationService.createConsultation(consultationDTO);
        return new ResponseEntity<>(healthConsultationService.convertToDTO(createdConsultation), HttpStatus.CREATED);
    }

    // Update consultation
    @PutMapping("/{consultationId}")
    public ResponseEntity<HealthConsultationDTO> updateConsultation(
            @PathVariable int consultationId,
            @RequestBody HealthConsultationUpdateDTO updateDTO) {

        // First, get the existing consultation to preserve creation information
        Optional<HealthConsultation> existingConsultationOpt = healthConsultationService.getConsultationById(consultationId);
        if (!existingConsultationOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // We only pass the update-related fields to the service
        // CreatedByNurseID and CreatedByNurseName will not be changed
        HealthConsultation updatedConsultation = healthConsultationService.updateConsultationStatus(
            consultationId,
            updateDTO.getStatus(),
            updateDTO.getReason(),
            updateDTO.getUpdatedByNurseID());

        if (updatedConsultation != null) {
            return new ResponseEntity<>(healthConsultationService.convertToDTO(updatedConsultation), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
