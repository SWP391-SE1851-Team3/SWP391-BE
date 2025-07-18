package com.team_3.School_Medical_Management_System.Model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vaccination_records {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer VaccinationRecordID;
    private String notes;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BatchID")
    private Vaccine_Batches vaccineBatches;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StudentID")
    private Student student;
    private LocalDateTime observation_time;
    private String symptoms;
    private String severity;
    private String observation_notes;
    @ManyToOne
    @JoinColumn(name = "CreatedByNurseID")
    private SchoolNurse createdByNurse;
    @ManyToOne
    @JoinColumn(name = "UpdatedByNurseID")
    private SchoolNurse updatedByNurse;
    @ManyToOne
    @JoinColumn(name = "consent_id")
    private Consent_forms consentForm;
    private String status;
}
