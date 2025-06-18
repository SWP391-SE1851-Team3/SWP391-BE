package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Transactional
@Data
public class MedicalEventUpdateDTO {
    private Integer eventId;
    private String usageMethod;
    private Boolean isEmergency;
    private Boolean hasParentBeenInformed;
    private String temperature;
    private String heartRate;
    private LocalDateTime eventDateTime;
    private Integer parentId;
    private Integer studentId;
    private String note;
    private String result;
    private String processingStatus;

    public MedicalEventUpdateDTO(Integer eventId, String usageMethod, Boolean isEmergency, Boolean hasParentBeenInformed, String temperature, String heartRate, LocalDateTime eventDateTime, Integer parentId, Integer studentId, String note, String result, String processingStatus) {
        this.eventId = eventId;
        this.usageMethod = usageMethod;
        this.isEmergency = isEmergency;
        this.hasParentBeenInformed = hasParentBeenInformed;
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.eventDateTime = eventDateTime;
        this.parentId = parentId;
        this.studentId = studentId;
        this.note = note;
        this.result = result;
        this.processingStatus = processingStatus;
    }
    public MedicalEventUpdateDTO() {
    }
}
