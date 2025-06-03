package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "MedicalEventTypeMapping")
@IdClass(MedicalEventTypeMappingId.class)
public class MedicalEventTypeMapping {
    @Id
    @ManyToOne
    @JoinColumn(name = "EventID")
    private MedicalEvent event;

    @Id
    @ManyToOne
    @JoinColumn(name = "EventTypeID")
    private MedicalEventType eventType;
}
