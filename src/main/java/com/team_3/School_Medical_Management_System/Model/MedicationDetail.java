package com.team_3.School_Medical_Management_System.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private MedicationSubmission medicationSubmission;

    private String medicineName;
    private String dosage;
    private String timeToUse;
    private String note;
}
