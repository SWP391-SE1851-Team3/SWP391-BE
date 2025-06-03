package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data

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
}
