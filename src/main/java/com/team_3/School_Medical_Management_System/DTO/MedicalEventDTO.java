package com.team_3.School_Medical_Management_System.DTO;

import com.team_3.School_Medical_Management_System.Model.Student;
import lombok.*;
import org.aspectj.weaver.loadtime.definition.LightXMLParser;

import java.time.LocalDateTime;
import java.util.List;

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
    private Integer studentId;
    private String tpyeName;
    //private List<Student> student;


    public MedicalEventDTO(Integer eventId, String usageMethod, boolean isEmergency, boolean hasParentBeenInformed,
                           String temperature, String heartRate, LocalDateTime eventDateTime,
                           Integer parentId, Integer studentId) {
        this.eventId = eventId;
        this.usageMethod = usageMethod;
        this.isEmergency = isEmergency;
        this.hasParentBeenInformed = hasParentBeenInformed;
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.eventDateTime = eventDateTime;
        this.parentId = parentId;
        this.studentId = studentId;
      //  this.tpyeName = tpyeName;
      //  this.student = student;
    }

    public MedicalEventDTO() {
    }
}
