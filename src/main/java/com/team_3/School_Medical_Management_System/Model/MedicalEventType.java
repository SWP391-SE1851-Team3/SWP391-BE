package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "MedicalEventType")
public class MedicalEventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventTypeID;

    @Column(nullable = false)
    private String typeName;
}
