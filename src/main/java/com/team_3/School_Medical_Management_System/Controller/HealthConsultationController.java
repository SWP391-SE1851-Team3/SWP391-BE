package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.HealthConsultationDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthConsultationUpdateDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.HealthConsultationServiceInterface;
import com.team_3.School_Medical_Management_System.Model.HealthConsultation;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import com.team_3.School_Medical_Management_System.Service.HealthConsultationService;
import com.team_3.School_Medical_Management_System.Service.SchoolNurseService;
import io.swagger.v3.oas.annotations.Operation;
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
    private HealthConsultationServiceInterface healthConsultationService;

    @Autowired
    private SchoolNurseService schoolNurseService;

    // Lấy tất cả các tư vấn sức khỏe
    @GetMapping("/all")
    @Operation(summary = "Lấy tất cả các tư vấn sức khỏe")
    public ResponseEntity<List<HealthConsultationDTO>> getAllConsultations() {
        List<HealthConsultation> consultations = healthConsultationService.getAllConsultations();
        List<HealthConsultationDTO> consultationDTOs = consultations.stream()
                .map(healthConsultationService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(consultationDTOs, HttpStatus.OK);
    }

    // Lấy tư vấn sức khỏe theo trạng thái (chờ xử lý/đã hoàn thành)
    @GetMapping("/status/{status}")
    @Operation(summary = "Lấy tư vấn sức khỏe theo trạng thái (chờ xử lý/đã hoàn thành)")
    public ResponseEntity<List<HealthConsultationDTO>> getConsultationsByStatus(@PathVariable String status) {
        List<HealthConsultation> consultations = healthConsultationService.getConsultationsByStatus(status);
        List<HealthConsultationDTO> consultationDTOs = consultations.stream()
                .map(healthConsultationService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(consultationDTOs, HttpStatus.OK);
    }

    // Lấy tư vấn sức khỏe cho một học sinh cụ thể
    @GetMapping("/student/{studentId}")
    @Operation(summary = "Lấy tư vấn sức khỏe cho một học sinh cụ thể")
    public ResponseEntity<List<HealthConsultationDTO>> getConsultationsByStudent(@PathVariable int studentId) {
        List<HealthConsultation> consultations = healthConsultationService.getConsultationsByStudent(studentId);
        List<HealthConsultationDTO> consultationDTOs = consultations.stream()
                .map(healthConsultationService::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(consultationDTOs, HttpStatus.OK);
    }

    // Tạo mới tư vấn sức khỏe
    @PostMapping
    @Operation(summary = "Tạo mới tư vấn sức khỏe")
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

    // Cập nhật tư vấn sức khỏe
    @PutMapping("/{consultationId}")
    @Operation(summary = "Cập nhật tư vấn sức khỏe")
    public ResponseEntity<HealthConsultationUpdateDTO> updateConsultation(
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
            updateDTO.getLocation(),
            updateDTO.getConsultDate(),
            updateDTO.getUpdatedByNurseID());

        if (updatedConsultation != null) {
            // Convert to update DTO to return only the fields that were sent in request
            HealthConsultationUpdateDTO responseDTO = healthConsultationService.convertToUpdateDTO(updatedConsultation);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
