package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.HealthCheckStudentSimplifiedDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentCreateDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentUpdateDTO;
import com.team_3.School_Medical_Management_System.Model.HealthCheck_Student;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import com.team_3.School_Medical_Management_System.Service.HealthCheckStudentService;
import com.team_3.School_Medical_Management_System.Service.SchoolNurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/health-check-results")
public class HealthCheckStudentController {

    @Autowired
    private HealthCheckStudentService healthCheckStudentService;

    @Autowired
    private SchoolNurseService schoolNurseService;

    // Record health check results using new DTO (without CheckID)
    @PostMapping("/create")
    public ResponseEntity<HealthCheck_Student> createHealthCheckResults(@RequestBody HealthCheck_StudentDTO dto) {
        if (dto.getCreatedByNurseID() != null && dto.getCreatedByNurseID() > 0) {
            // Get the nurse by ID and set the name if found
            SchoolNurse nurse = schoolNurseService.GetSchoolNursesById(dto.getCreatedByNurseID());
            if (nurse != null) {
                // Always set creator nurse name from nurse ID
                dto.setCreatedByNurseName(nurse.getFullName());
            }
        }

        if (dto.getUpdatedByNurseID() != null && dto.getUpdatedByNurseID() > 0) {
            SchoolNurse nurse = schoolNurseService.GetSchoolNursesById(dto.getUpdatedByNurseID());
            if (nurse != null) {
                // Always set updater nurse name from nurse ID
                dto.setUpdatedByNurseName(nurse.getFullName());
            }
        }
        HealthCheck_Student savedResult = healthCheckStudentService.createHealthCheckResults(dto);
        return new ResponseEntity<>(savedResult, HttpStatus.CREATED);
    }

    // Get health check results for a student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<HealthCheckStudentSimplifiedDTO>> getHealthCheckResultsByStudent(@PathVariable int studentId) {
        List<HealthCheckStudentSimplifiedDTO> results = healthCheckStudentService.getSimplifiedHealthCheckResultsByStudent(studentId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    // Update health check results - modified to use UpdateDTO and handle update fields internally
    @PutMapping("/{id}")
    public ResponseEntity<HealthCheck_Student> updateHealthCheckResults(
            @PathVariable int id,
            @RequestBody HealthCheck_StudentUpdateDTO updateDTO,
            @RequestParam(required = false) Integer nurseID) {

        // Create a full DTO with update information added
        HealthCheck_StudentDTO fullDTO = new HealthCheck_StudentDTO();

        // Copy fields from updateDTO to fullDTO
        fullDTO.setCheckID(updateDTO.getCheckID());
        fullDTO.setStudentID(updateDTO.getStudentID());
        fullDTO.setHeight(updateDTO.getHeight());
        fullDTO.setWeight(updateDTO.getWeight());
        fullDTO.setVisionLeft(updateDTO.getVisionLeft());
        fullDTO.setVisionRight(updateDTO.getVisionRight());
        fullDTO.setHearing(updateDTO.getHearing());
        fullDTO.setDentalCheck(updateDTO.getDentalCheck());
        fullDTO.setTemperature(updateDTO.getTemperature());
        fullDTO.setOverallResult(updateDTO.getOverallResult());

        // Copy creation information
        fullDTO.setUpdate_at(updateDTO.getUpdate_at());
        fullDTO.setUpdatedByNurseID(updateDTO.getUpdatedByNurseID());
        // Set update information internally
        fullDTO.setUpdate_at(new Date());
        fullDTO.setUpdatedByNurseID(nurseID);

        // If nurseID is provided, get nurse name
        if (nurseID != null) {
            SchoolNurse nurse = schoolNurseService.GetSchoolNursesById(nurseID);
            if (nurse != null) {
                fullDTO.setUpdatedByNurseName(nurse.getFullName());
            }
        }

        HealthCheck_Student updatedResult = healthCheckStudentService.updateHealthCheckResults(id, fullDTO);
        return new ResponseEntity<>(updatedResult, HttpStatus.OK);
    }

    // Delete health check results
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthCheckResults(@PathVariable int id) {
        healthCheckStudentService.deleteHealthCheckResults(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/all")
    public ResponseEntity<List<HealthCheckStudentSimplifiedDTO>> getAllHealthCheckResults() {
        List<HealthCheckStudentSimplifiedDTO> results = healthCheckStudentService.getAllHealthCheckResultsSimplified();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
