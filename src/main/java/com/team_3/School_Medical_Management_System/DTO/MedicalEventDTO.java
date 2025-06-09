package com.team_3.School_Medical_Management_System.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter

public class MedicalEventDTO {
    private Integer eventId;
    private String usageMethod;
    private boolean isEmergency;
    private boolean hasParentBeenInformed;
    private String temperature;
    private String heartRate;
    private LocalDateTime eventDateTime;
    private Integer parentId;



    public MedicalEventDTO(String usageMethod, boolean isEmergency, boolean hasParentBeenInformed, String temperature, String heartRate, LocalDateTime eventDateTime, Integer parentId, Integer studentId, Integer eventTypeId) {
        //  this.eventId = eventId;
        this.usageMethod = usageMethod;
        this.isEmergency = isEmergency;
        this.hasParentBeenInformed = hasParentBeenInformed;
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.eventDateTime = eventDateTime;
        this.parentId = parentId;
      //  this.studentId = studentId;

    }

    public MedicalEventDTO() {
    }
}
