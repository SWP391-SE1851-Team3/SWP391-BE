package com.team_3.School_Medical_Management_System.DTO;

public class MedicalSupplyQuantityDTO {
    private Integer medicalSupplyId;
    private String supplyName;
    private String unit;
    private Integer quantityUsed;

    // Constructor mặc định
    public MedicalSupplyQuantityDTO() {
    }

    // Constructor với tham số
    public MedicalSupplyQuantityDTO(Integer medicalSupplyId, String supplyName, String unit, Integer quantityUsed) {
        this.medicalSupplyId = medicalSupplyId;
        this.supplyName = supplyName;
        this.unit = unit;
        this.quantityUsed = quantityUsed;
    }

    // Getters và Setters
    public Integer getMedicalSupplyId() {
        return medicalSupplyId;
    }

    public void setMedicalSupplyId(Integer medicalSupplyId) {
        this.medicalSupplyId = medicalSupplyId;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(Integer quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

}
