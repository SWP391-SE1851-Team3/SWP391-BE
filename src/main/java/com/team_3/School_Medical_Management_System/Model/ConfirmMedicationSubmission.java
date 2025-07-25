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
@Table(name = "Confirm_MedicationSubmission")
@Setter
@Getter
public class ConfirmMedicationSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int confirmId;
    private int medicationSubmissionId;
    private Integer nurseId;
    private String reason;

    private String evidence;

    private String status;
}

