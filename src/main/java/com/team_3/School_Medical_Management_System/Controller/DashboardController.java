package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Service.DashboardServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/dashboard")
@Tag(name = "Dashboard Report", description = "API báo cáo dashboard cho ADMIN")

public class DashboardController {

    private DashboardServiceImpl dashboardService;

    @Autowired
    public DashboardController(DashboardServiceImpl dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Operation(summary = "Lấy toàn bộ báo cáo dashboard")
    @GetMapping("/full-report")
    public ResponseEntity<DashboardReportModel> getFullReport() {

        try {
            DashboardReportModel report = dashboardService.getFullReport();
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Lấy thống kê tổng quan hệ thống
     */
    @Operation(summary = "Thống kê tổng quan hệ thống")
    @GetMapping("/system-stats")
    public ResponseEntity<SystemStats> getSystemStats() {


        try {
            SystemStats stats = dashboardService.getSystemStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {

            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Lấy thống kê sự kiện y tế
     */
    @Operation(summary = "Thống kê sự kiện y tế")
    @GetMapping("/medical-events")
    public ResponseEntity<MedicalEventStats> getMedicalEventStats() {


        try {
            MedicalEventStats stats = dashboardService.getMedicalEventStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {

            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Lấy thống kê tiêm chủng
     */
    @Operation(summary = "Thống kê tiêm chủng")
    @GetMapping("/vaccination")
    public ResponseEntity<VaccinationStats> getVaccinationStats() {


        try {
            VaccinationStats stats = dashboardService.getVaccinationStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {

            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Lấy thống kê khám sức khỏe
     */
    @Operation(summary = "Thống kê khám sức khỏe")
    @GetMapping("/health-check")
    public ResponseEntity<HealthCheckStats> getHealthCheckStats() {


        try {
            HealthCheckStats stats = dashboardService.getHealthCheckStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {

            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Lấy thống kê gửi thuốc
     */
    @Operation(summary = "Thống kê gửi thuốc")
    @GetMapping("/medication")
    public ResponseEntity<MedicationStats> getMedicationStats() {


        try {
            MedicationStats stats = dashboardService.getMedicationStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {

            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Health check endpoint
     */
    @Operation(summary = "Kiểm tra service")
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Dashboard Service OK - " + java.time.LocalDateTime.now());
    }
}
