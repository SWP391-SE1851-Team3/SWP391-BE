package com.team_3.School_Medical_Management_System.DTO;

import com.team_3.School_Medical_Management_System.Model.Student;
import lombok.*;
import org.aspectj.weaver.loadtime.definition.LightXMLParser;
import org.springframework.web.bind.annotation.RequestParam;

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
    private Integer parentID;
    private Integer studentId;
    private String typeName;
    //private List<Student> student;
    //@RequestParam

    //@RequestParam
    private
    String note;
    // @RequestParam
    private
    String result;
    // @RequestParam
    private
    String processingStatus;
    //  @RequestParam
    private
    Integer eventTypeId;

    public MedicalEventDTO(Integer eventId, String usageMethod, boolean isEmergency, boolean hasParentBeenInformed, String temperature, String heartRate, LocalDateTime eventDateTime, Integer parentID, Integer studentId, String typeName, int studentId1, String note, String result, String processingStatus, Integer eventTypeId) {
        this.eventId = eventId;
        this.usageMethod = usageMethod;
        this.isEmergency = isEmergency;
        this.hasParentBeenInformed = hasParentBeenInformed;
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.eventDateTime = eventDateTime;
        this.parentID = parentID;
        this.studentId = studentId;
        this.typeName = typeName;
        this.studentId = studentId1;
        this.note = note;
        this.result = result;
        this.processingStatus = processingStatus;
        this.eventTypeId = eventTypeId;
    }

    public MedicalEventDTO() {
    }
}
