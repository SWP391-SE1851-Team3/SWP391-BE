package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine_Batches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer BatchID;
    private String dot;
    private LocalDateTime scheduled_date;
    private String location;
    private String status;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "CreatedByNurseID")
    private SchoolNurse createdByNurse;

    @ManyToOne
    @JoinColumn(name = "UpdatedByNurseID")
    private SchoolNurse updatedByNurse;
    @ManyToOne
    @JoinColumn(name = "VaccineTypeID")
    private Vaccine_Types vaccineType;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
