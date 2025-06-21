package com.team_3.School_Medical_Management_System.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @OneToMany(mappedBy = "medicationSubmission", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MedicationDetail> medicationDetails;

    private java.time.LocalDateTime submissionDate;
    public java.time.LocalDateTime getSubmissionDate() {
        return submissionDate;
    }
    public void setSubmissionDate(java.time.LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

}
