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
public class Vaccine_Batches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer BatchID;
    private String dot;
    private Integer quantity_received;
    private LocalDateTime scheduled_date;
    private String location;
    private String status;
    private String notes;
    @ManyToOne
    @JoinColumn(name = "NurseID")
    private SchoolNurse nurse;
    @ManyToOne
    @JoinColumn(name = "VaccineTypeID")
    private Vaccine_Types vaccineType;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
