package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "MedicalEventNurseMapping")
@IdClass(MedicalEventNurseMappingId.class)
public class MedicalEventNurseMapping {
    @Id
    @ManyToOne
    @JoinColumn(name = "NurseID")
    private SchoolNurse nurse;

    @Id
    @ManyToOne
    @JoinColumn(name = "EventID")
    private MedicalEvent event;
}
