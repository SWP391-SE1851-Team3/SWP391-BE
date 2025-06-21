package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MedicalEventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventTypeId;
    private String typeName;
}
