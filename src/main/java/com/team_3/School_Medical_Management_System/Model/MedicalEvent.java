package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
// Luư thông tin của sự kiện y tế vào class này
@Table(name = "MedicalEvent")
public class MedicalEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventID;

    private String usageMethod;
    private Boolean isEmergency;
    private Boolean hasParentBeenInformed;
    private String temperature;
    private String heartRate;
    private LocalDateTime eventDateTime;

 @ManyToOne
 @JoinColumn(name = "ParentID")
 private Parent parent;

    public MedicalEvent() {
    }
    public MedicalEvent(Integer eventID, String usageMethod, Boolean isEmergency, Boolean hasParentBeenInformed, String temperature, String heartRate, LocalDateTime eventDateTime, Parent parent) {
        this.eventID = eventID;
        this.usageMethod = usageMethod;
        this.isEmergency = isEmergency;
        this.hasParentBeenInformed = hasParentBeenInformed;
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.eventDateTime = eventDateTime;
        this.parent = parent;
    }
}
