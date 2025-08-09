package com.team_3.School_Medical_Management_System.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class StudentMedicalEventDto {
    @JsonProperty("studentName")
    private String studentNameAndClassName;

    @JsonProperty("eventType")// Học sinh (FullName + ClassName từ Student)
    private List<String> eventType;
    @JsonProperty("time")
    @JsonFormat(pattern = "HH:mm, dd/MM/yyyy")// Loại sự kiện (usageMethod từ MedicalEvent)
    private LocalDateTime time;
    @JsonProperty("processingStatus")// Thời gian (eventDateTime từ MedicalEvent)
    private String processingStatus;
    @JsonProperty("actions")// Trạng thái (processingStatus từ MedicalEventDetails)
    private String actions; // Hành động (chuỗi rỗng hoặc tùy chỉnh)
    private  Integer eventId;
    private Long eventDetailsID;
    public StudentMedicalEventDto(String studentNameAndClassName, List<String> eventType, LocalDateTime time, String processingStatus, String actions, Integer eventId,Long eventDetailsID) {
        this.studentNameAndClassName = studentNameAndClassName;
        this.eventType = eventType;
        this.time = time;
        this.processingStatus = processingStatus;
        this.actions = actions;
        this.eventId = eventId;
        this.eventDetailsID = eventDetailsID;
    }

    public StudentMedicalEventDto() {
    }
}
