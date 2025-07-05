package com.team_3.School_Medical_Management_System.DTO;

import java.util.Date;

public class HealthCheckStudentUpdateResponseDTO {
    private int checkID;
    private Integer studentID;
    private Float height;
    private Float weight;
    private String visionLeft;
    private String visionRight;
    private String hearing;
    private String dentalCheck;
    private String temperature;
    private Float bmi;
    private String overallResult;
    private Date create_at;
    private Date update_at;
    private int updatedByNurseID;
    private String updatedByNurseName;

    // Getters and setters
    public Integer getCheckID() { return checkID; }
    public void setCheckID(Integer checkID) { this.checkID = checkID; }
    public Integer getStudentID() { return studentID; }
    public void setStudentID(Integer studentID) { this.studentID = studentID; }
    public Float getHeight() { return height; }
    public void setHeight(Float height) { this.height = height; }
    public Float getWeight() { return weight; }
    public void setWeight(Float weight) { this.weight = weight; }
    public String getVisionLeft() { return visionLeft; }
    public void setVisionLeft(String visionLeft) { this.visionLeft = visionLeft; }
    public String getVisionRight() { return visionRight; }
    public void setVisionRight(String visionRight) { this.visionRight = visionRight; }
    public String getHearing() { return hearing; }
    public void setHearing(String hearing) { this.hearing = hearing; }
    public String getDentalCheck() { return dentalCheck; }
    public void setDentalCheck(String dentalCheck) { this.dentalCheck = dentalCheck; }
    public String getTemperature() { return temperature; }
    public void setTemperature(String temperature) { this.temperature = temperature; }
    public Float getBmi() { return bmi; }
    public void setBmi(Float bmi) { this.bmi = bmi; }
    public String getOverallResult() { return overallResult; }
    public void setOverallResult(String overallResult) { this.overallResult = overallResult; }
    public Date getCreate_at() { return create_at; }
    public void setCreate_at(Date create_at) { this.create_at = create_at; }
    public Date getUpdate_at() { return update_at; }
    public void setUpdate_at(Date update_at) { this.update_at = update_at; }
    public String getUpdatedByNurseName() { return updatedByNurseName; }
    public void setUpdatedByNurseName(String updatedByNurseName) { this.updatedByNurseName = updatedByNurseName; }
    public int getUpdatedByNurseID() { return updatedByNurseID; }
    public void setUpdatedByNurseID(int updatedByNurseID) { this.updatedByNurseID = updatedByNurseID; }
}

