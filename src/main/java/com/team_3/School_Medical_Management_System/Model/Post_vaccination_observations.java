package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Post_vaccination_observations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer observation_id;
    private LocalDateTime observation_time;
    private String symptoms;
    private String severity;
    private String notes;
    private String status;
    @ManyToOne
    @JoinColumn(name = "VaccinationRecordID")
    private Vaccination_records vaccination_records;
    @ManyToOne
    @JoinColumn(name = "CreatedByNurseID")
    private SchoolNurse createdByNurse;

    @ManyToOne
    @JoinColumn(name = "UpdatedByNurseID")
    private SchoolNurse updatedByNurse;


}
