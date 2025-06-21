package com.team_3.School_Medical_Management_System.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


public class StudentsDTO {
    private int studentID;
    private int gender;

    private String fullName;

    private String className;
    private int isActive;
    private int parentID;

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public StudentsDTO(int studentID, int gender, String fullName, String className, int isActive, int parentID) {
        this.studentID = studentID;
        this.gender = gender;
        this.fullName = fullName;
        this.className = className;
        this.isActive = isActive;
        this.parentID = parentID;
    }
    public StudentsDTO() {
    }
}
