package com.team_3.School_Medical_Management_System.Model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vaccination_records {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int VaccinationRecordID;
    private String notes;
    @ManyToOne
    @JoinColumn(name = "BatchID")
    private Vaccine_Batches vaccineBatches;
    @ManyToOne
    @JoinColumn(name = "StudentID")
    private Student student;
    private LocalDateTime observation_time;
    private String symptoms;
    private String severity;
    private String observation_notes;
    @ManyToOne
    @JoinColumn(name = "NurseID")
    private SchoolNurse nurse;
    private String status;

}
