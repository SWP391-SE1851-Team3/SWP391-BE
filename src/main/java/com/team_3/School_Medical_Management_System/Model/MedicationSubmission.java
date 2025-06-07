package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@ToString
@Entity
@Table
@Setter
@Getter
public class MedicationSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medicationSubmissionId;

    private int parentId;
    private int studentId;
    private String medicationName;
    private LocalDateTime medicationSubmissionDate;
    private int frequencyPerDay;
    private String DurationDays;
    private String Dosage;
    private boolean requestSupervisor;
    private Date startDate;
    private Date endDate;
    private String notes;

//    @Enumerated(EnumType.STRING)
//    private SubmissionStatus status;

    private LocalDateTime submissionDate;
    private LocalDateTime processedDate;
    private LocalDateTime administeredDate;
    private String rejectionReason;
    private String administrationNotes;

//    public enum SubmissionStatus {
//        PENDING,
//        APPROVED,
//        REJECTED,
//        ADMINISTERED
//    }

    @PrePersist
    public void prePersist() {
        this.submissionDate = LocalDateTime.now();
//        this.status = SubmissionStatus.PENDING;
    }
}
