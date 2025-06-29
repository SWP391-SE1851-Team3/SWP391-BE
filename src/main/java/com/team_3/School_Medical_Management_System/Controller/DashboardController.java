package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.MedicalSupplyReportDTO;
import com.team_3.School_Medical_Management_System.DTO.SupplyCategoryDTO;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Service.DashboardServiceImpl;
import com.team_3.School_Medical_Management_System.Service.MedicalSupplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard")
@Tag(name = "Dashboard Report", description = "API báo cáo dashboard cho ADMIN")

public class DashboardController {
    private MedicalSupplyService medicalSupplyService;
    private DashboardServiceImpl dashboardService;

    @Autowired
    public DashboardController(MedicalSupplyService medicalSupplyService, DashboardServiceImpl dashboardService) {
        this.medicalSupplyService = medicalSupplyService;
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

    @Operation(summary = "Thống kê vật tư y tế")
    @GetMapping("/reportMedicalSupply")
    public ResponseEntity<List<MedicalSupplyReportDTO>> getMedicalSupplyReport(
            @RequestParam(required = false) Integer categoryId) {
        List<MedicalSupplyReportDTO> report = medicalSupplyService.getMedicalSupplyReport(categoryId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/low-stock-MedicalSupply")
    public ResponseEntity<List<MedicalSupplyReportDTO>> getLowStockReport() {
        List<MedicalSupplyReportDTO> lowStockReport = medicalSupplyService.getLowStockReport();
        return ResponseEntity.ok(lowStockReport);
    }
    @Operation(summary = "Lấy full báo cáo vật tư y tế theo danh mục")
    @GetMapping("/reportSupplyBCategory")
    public ResponseEntity<List<SupplyCategoryDTO>> getMedicalSupplyReportByCategory() {
        List<SupplyCategoryDTO> report = medicalSupplyService.getAllCategories();
        return ResponseEntity.ok(report);
    }

}
