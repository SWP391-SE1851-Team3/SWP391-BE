package com.team_3.School_Medical_Management_System.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Date;

public class StudentMedicalEventDto {
    @JsonProperty("studentName")
    private String studentNameAndClassName;

    @JsonProperty("eventType")// Học sinh (FullName + ClassName từ Student)
    private String eventType;
    @JsonProperty("time")
    @JsonFormat(pattern = "HH:mm, dd/MM/yyyy")// Loại sự kiện (usageMethod từ MedicalEvent)
    private LocalDateTime time;
    @JsonProperty("status")// Thời gian (eventDateTime từ MedicalEvent)
    private String status;
    @JsonProperty("actions")// Trạng thái (processingStatus từ MedicalEventDetails)
    private String actions; // Hành động (chuỗi rỗng hoặc tùy chỉnh)

    public StudentMedicalEventDto(String studentNameAndClassName, String eventType, LocalDateTime time, String status, String actions) {
        this.studentNameAndClassName = studentNameAndClassName;
        this.eventType = eventType;
        this.time = time;
        this.status = status;
        this.actions = actions;
    }

    public StudentMedicalEventDto() {
    }
}
