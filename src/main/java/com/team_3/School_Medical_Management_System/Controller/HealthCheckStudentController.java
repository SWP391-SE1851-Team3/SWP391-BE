package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentCreateDTO;
import com.team_3.School_Medical_Management_System.DTO.HealthCheck_StudentDTO;
import com.team_3.School_Medical_Management_System.Model.HealthCheck_Student;
import com.team_3.School_Medical_Management_System.Service.HealthCheckStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-check-results")
public class HealthCheckStudentController {

    @Autowired
    private HealthCheckStudentService healthCheckStudentService;
    // Record health check results using new DTO (without CheckID)
    @PostMapping("/create")
    public ResponseEntity<HealthCheck_Student> createHealthCheckResults(@RequestBody HealthCheck_StudentCreateDTO dto) {
        HealthCheck_Student savedResult = healthCheckStudentService.createHealthCheckResults(dto);
        return new ResponseEntity<>(savedResult, HttpStatus.CREATED);
    }

    // Get health check results for a student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<HealthCheck_Student>> getHealthCheckResultsByStudent(@PathVariable int studentId) {
        List<HealthCheck_Student> results = healthCheckStudentService.getHealthCheckResultsByStudent(studentId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    // Update health check results
    @PutMapping("/{id}")
    public ResponseEntity<HealthCheck_Student> updateHealthCheckResults(@PathVariable int id, @RequestBody HealthCheck_StudentDTO dto) {
        HealthCheck_Student updatedResult = healthCheckStudentService.updateHealthCheckResults(id, dto);
        return new ResponseEntity<>(updatedResult, HttpStatus.OK);
    }

    // Delete health check results
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthCheckResults(@PathVariable int id) {
        healthCheckStudentService.deleteHealthCheckResults(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
