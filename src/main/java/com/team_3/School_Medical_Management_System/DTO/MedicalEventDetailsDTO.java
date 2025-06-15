package com.team_3.School_Medical_Management_System.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MedicalEventDetailsDTO {
    private Integer eventId;
    private String usageMethod;
    private Boolean isEmergency;
    private Boolean hasParentBeenInformed;
    private String temperature;
    private String heartRate;
    private LocalDateTime eventDateTime;
    //  private Integer parentId;
   // private Integer studentId;
    private String note;
    private String result;
    private String processingStatus;


    private Integer studentId;
    private String fullName;
private int gender;
    private String className;
    private List<String> eventTypeNames;

    public MedicalEventDetailsDTO(Integer eventId, String usageMethod, Boolean isEmergency, Boolean hasParentBeenInformed, String temperature, String heartRate, LocalDateTime eventDateTime, String note, String result, String processingStatus, Integer studentId, String fullName, int gender, String className, List<String> eventTypeNames) {
        this.eventId = eventId;
        this.usageMethod = usageMethod;
        this.isEmergency = isEmergency;
        this.hasParentBeenInformed = hasParentBeenInformed;
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.eventDateTime = eventDateTime;
        this.note = note;
        this.result = result;
        this.processingStatus = processingStatus;
        this.studentId = studentId;
        this.fullName = fullName;
        this.gender = gender;
        this.className = className;
        this.eventTypeNames = eventTypeNames;
    }

    public MedicalEventDetailsDTO() {
    }
}
