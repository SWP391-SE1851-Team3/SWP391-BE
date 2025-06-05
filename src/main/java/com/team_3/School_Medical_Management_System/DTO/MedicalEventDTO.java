package com.team_3.School_Medical_Management_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class MedicalEventDTO {

    private Integer eventId;
    private String usageMethod;
    private Boolean isEmergency;
    private Boolean hasParentBeenInformed;
    private String temperature;
    private String heartRate;
    private LocalDateTime eventDateTime;
    private Integer parentId;
    private Integer studentId;
    private String eventType;
    private String note;
    private String result;
    private String processingStatus;


}
