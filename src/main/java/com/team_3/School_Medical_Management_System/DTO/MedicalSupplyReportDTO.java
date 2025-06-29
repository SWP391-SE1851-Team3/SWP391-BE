package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;

@Data
public class MedicalSupplyReportDTO {
    private String supplyName;
    private String categoryName;
    private String unit;
    private Integer quantityAvailable;
    private Integer reorderLevel;
    private Boolean isBelowReorderLevel;
}
