package com.team_3.School_Medical_Management_System.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Vaccination_schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int schedule_id;
    @ManyToOne
    @JoinColumn(name = "Vaccine_id")
    private Vaccines Vaccine;
    private LocalDateTime scheduled_date;
    private String location;
    @ManyToOne
    @JoinColumn(name = "NurseID")
    private SchoolNurse Nurse;
    private String status;
    private String notes;

}

