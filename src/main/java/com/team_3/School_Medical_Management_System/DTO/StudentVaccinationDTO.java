package com.team_3.School_Medical_Management_System.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class StudentVaccinationDTO {
    private Integer studentId;
    @NotBlank(message = "FullName Not allow empty")
    private String fullName;
    @NotBlank(message = "ClassName Not allow empty")
    private String className;
    private String observationNotes;
    private String vaccineTypeName;
    private LocalDateTime observationTime;
    private String status;
    public StudentVaccinationDTO(Integer studentId, String fullName, String className,
                                 String observationNotes, String vaccineTypeName,
                                 LocalDateTime observationTime,String status) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.className = className;
        this.observationNotes = observationNotes;
        this.vaccineTypeName = vaccineTypeName;
        this.observationTime = observationTime;
        this.status = status;
    }
}