package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@Entity
@Table
@Setter
@Getter
public class ConfirmMedicationSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int confirmId;
    private int medicationSubmissionId;
    private int nurseId;
    private String evidence;

    private LocalDateTime confirmedAt;
    private LocalDateTime medicationTakenAt;

    @Enumerated(EnumType.STRING)
    private confirmMedicationSubmissionStatus status;

    public enum confirmMedicationSubmissionStatus {
        PENDING,
        APPROVED,
        REJECTED,
        ADMINISTERED
    }

    @Enumerated(EnumType.STRING)
    private confirmMedicationSubmissionReceivedMedicine receivedMedicine;

    public enum confirmMedicationSubmissionReceivedMedicine {
        YES,
        NO
    }
}
