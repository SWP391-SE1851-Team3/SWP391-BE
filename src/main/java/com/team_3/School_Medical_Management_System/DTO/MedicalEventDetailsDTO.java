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
    private Integer createdByNurseId;
    private String createdByNurseName;
    private Integer updatedByNurseId;
    private String updatedByNurseName;


    public MedicalEventDetailsDTO() {
    }
}
