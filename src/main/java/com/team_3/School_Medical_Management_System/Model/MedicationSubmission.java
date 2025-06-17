package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@ToString
@Entity
@Setter
@Getter
@Table(name = "MedicationSubmission")

public class MedicationSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medicationSubmissionId;

    private int parentId;
    private int studentId;
    private String medicineImage;

//    @Enumerated(EnumType.STRING)
//    private SubmissionStatus status;

    private LocalDateTime submissionDate;
    private LocalDateTime processedDate;
    private LocalDateTime administeredDate;
    private String rejectionReason;
    private String administrationNotes;

    @OneToMany(mappedBy = "medicationSubmission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicationDetail> medicationDetails;

    @PrePersist
    public void prePersist() {
        this.submissionDate = LocalDateTime.now();
    }
}
