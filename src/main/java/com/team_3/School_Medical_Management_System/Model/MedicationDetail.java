package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Table(name = "MedicationDetail")
@Setter
@Getter

public class MedicationDetail {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medicationDetailId;
    @ManyToOne
    @JoinColumn(name = "medicationSubmissionId")
    private MedicationSubmission medicationSubmission;

    private String medicationName;
    private String dosage;
    private String timesToUse;
    private String notes;
}
