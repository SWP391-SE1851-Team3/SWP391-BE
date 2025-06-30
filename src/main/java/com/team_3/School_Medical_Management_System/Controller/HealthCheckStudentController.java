package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.HealthCheckStudentSimplifiedDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentCreateDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentUpdateDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheckStudentUpdateResponseDTO;
import com.team_3.School_Medical_Management_System.Model.HealthCheck_Student;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import com.team_3.School_Medical_Management_System.Service.HealthCheckStudentService;
import com.team_3.School_Medical_Management_System.Service.SchoolNurseService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Tạo kết quả kiểm tra sức khỏe mới cho học sinh")
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
    @Operation(summary = "Lấy kết quả kiểm tra sức khỏe cho một học sinh cụ thể theo ID học sinh")
    public ResponseEntity<List<HealthCheckStudentSimplifiedDTO>> getHealthCheckResultsByStudent(@PathVariable int studentId) {
        List<HealthCheckStudentSimplifiedDTO> results = healthCheckStudentService.getSimplifiedHealthCheckResultsByStudent(studentId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/health-check-schedule/{healthScheduleId}")
    @Operation(summary = "Lấy kết quả kiểm tra sức khỏe theo ID lịch kiểm tra sức khỏe")
    public ResponseEntity<List<HealthCheckStudentSimplifiedDTO>> getHealthCheckResultsBySchedule(@PathVariable int healthScheduleId) {
        List<HealthCheckStudentSimplifiedDTO> results = healthCheckStudentService.getSimplifiedHealthCheckResultsBySchedule(healthScheduleId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    // Update health check results - modified to use UpdateDTO and handle update fields internally
    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật kết quả kiểm tra sức khỏe cho học sinh")
    public ResponseEntity<HealthCheckStudentUpdateResponseDTO> updateHealthCheckResults(
            @PathVariable int id,
            @RequestBody HealthCheck_StudentUpdateDTO updateDTO,
            @RequestParam(required = false) Integer nurseID) {
        // Lấy bản ghi cũ
        HealthCheck_Student old = healthCheckStudentService.getHealthCheckStudentById(id);
        if (old == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HealthCheck_StudentDTO fullDTO = new HealthCheck_StudentDTO();
        // Copy fields từ updateDTO sang fullDTO
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
        // Logic set nurse
        if ((old.getCreatedByNurseID() == null || old.getCreatedByNurseID() == 0) && nurseID != null) {
            // Lần đầu sửa
            fullDTO.setCreatedByNurseID(nurseID);
        } else {
            // Các lần sau
            fullDTO.setCreatedByNurseID(old.getCreatedByNurseID());
            if (nurseID != null) {
                fullDTO.setUpdatedByNurseID(nurseID);
            }
        }
        // Gọi service update
        HealthCheck_Student updated = healthCheckStudentService.updateHealthCheckResults(id, fullDTO);
        if (updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Convert sang DTO trả về
        HealthCheckStudentUpdateResponseDTO response = new HealthCheckStudentUpdateResponseDTO();
        response.setCheckID(updated.getCheckID());
        response.setStudentID(updated.getStudentID());
        response.setHeight(updated.getHeight());
        response.setWeight(updated.getWeight());
        response.setVisionLeft(updated.getVisionLeft());
        response.setVisionRight(updated.getVisionRight());
        response.setHearing(updated.getHearing());
        response.setDentalCheck(updated.getDentalCheck());
        response.setTemperature(updated.getTemperature());
        response.setBmi(updated.getBmi());
        response.setOverallResult(updated.getOverallResult());
        response.setCreate_at(updated.getCreate_at());
        response.setUpdate_at(updated.getUpdate_at());
        if (updated.getUpdatedByNurseID() != null) {
            response.setUpdatedByNurseID(updated.getUpdatedByNurseID());
        } else {
            response.setUpdatedByNurseID(null);
        }
        if (updated.getCreatedByNurseID() != null) {
            response.setCreatedByNurseID(updated.getCreatedByNurseID());
        } else {
            response.setCreatedByNurseID(null);
        }
        String createdByNurseName = null;
        if (updated.getCreatedByNurseID() != null) {
            SchoolNurse nurse = schoolNurseService.GetSchoolNursesById(response.getCreatedByNurseID());
            if (nurse != null) {
                createdByNurseName = nurse.getFullName();
            }
        }
        response.setCreatedByNurseName(createdByNurseName);
        response.setUpdatedByNurseID(updated.getUpdatedByNurseID());
        String updatedByNurseName = null;
        if (updated.getUpdatedByNurseID() != null) {
            SchoolNurse nurse = schoolNurseService.GetSchoolNursesById(response.getUpdatedByNurseID());
            if (nurse != null) {
                updatedByNurseName = nurse.getFullName();
            }
        }
        response.setUpdatedByNurseName(updatedByNurseName);
        response.setCreate_at(updated.getCreate_at());
        response.setUpdate_at(updated.getUpdate_at());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete health check results
    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa kết quả kiểm tra sức khỏe theo ID")
    public ResponseEntity<Void> deleteHealthCheckResults(@PathVariable int id) {
        healthCheckStudentService.deleteHealthCheckResults(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/all")
    @Operation(summary = "Lấy tất cả kết quả kiểm tra sức khỏe cho tất cả học sinh")
    public ResponseEntity<List<HealthCheckStudentSimplifiedDTO>> getAllHealthCheckResults() {
        List<HealthCheckStudentSimplifiedDTO> results = healthCheckStudentService.getAllHealthCheckResultsSimplified();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
