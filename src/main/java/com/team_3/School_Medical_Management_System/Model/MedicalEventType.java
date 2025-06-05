package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MedicalEventType")
public class MedicalEventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventTypeID;

    @Column(nullable = false,columnDefinition = "Nvarchar(255)")
    private String typeName;
}
