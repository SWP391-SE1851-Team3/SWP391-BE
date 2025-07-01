package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.MedicalSupplyReportDTO;
import com.team_3.School_Medical_Management_System.DTO.SupplyCategoryDTO;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Service.DashboardServiceImpl;
import com.team_3.School_Medical_Management_System.Service.MedicalSupplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ResponseEntity<DashboardReportModel> getFullReport(

@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {

        try {
            DashboardReportModel report = dashboardService.getFullReport(startDate, endDate);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @Operation(summary = "Thống kê tổng quan hệ thống")
    @GetMapping("/system-stats")
    public ResponseEntity<SystemStats> getSystemStats(


    ) {


        try {
            SystemStats stats = dashboardService.getSystemStats();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {

            return ResponseEntity.internalServerError().build();
        }
    }


    @Operation(summary = "Thống kê sự kiện y tế")
    @GetMapping("/medical-events")
    public ResponseEntity<MedicalEventStats> getMedicalEventStats(@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                  @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {


        try {
            MedicalEventStats stats = dashboardService.getMedicalEventStats(startDate, endDate);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {

            return ResponseEntity.internalServerError().build();
        }
    }


    @Operation(summary = "Thống kê tiêm chủng")
    @GetMapping("/vaccination")
    public ResponseEntity<VaccinationStats> getVaccinationStats(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {


        try {
            VaccinationStats stats = dashboardService.getVaccinationStats(startDate, endDate);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {

            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Thống kê khám sức khỏe")
    @GetMapping("/health-check")
    public ResponseEntity<HealthCheckStats> getHealthCheckStats(@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {


        try {
            HealthCheckStats stats = dashboardService.getHealthCheckStats(startDate, endDate);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {

            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Thống kê gửi thuốc")
    @GetMapping("/medication")
    public ResponseEntity<MedicationStats> getMedicationStats(@RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                              @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {


        try {
            MedicationStats stats = dashboardService.getMedicationStats(startDate, endDate);
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
//    @Operation(summary = "Lấy full account của mỗi role")
//    @GetMapping("/full-account/{roleID}")
//    public ResponseEntity Liss getFullAccountByRole(@PathVariable int roleID) {
////        List<Account> accounts = dashboardService.getFullAccountByRole(roleID);
//        return ResponseEntity.ok(roleID);
}
