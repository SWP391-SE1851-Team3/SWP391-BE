package com.team_3.School_Medical_Management_System.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vaccines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Vaccine_id;
    private String Name;
    private String Manufacturer;
    private String Description;
    private String Recommended_ages;
    private LocalDateTime Vaccine_created_at;
    private LocalDateTime Vaccine_updated_at;
    private String dot;
    private Integer quantity_received;
    private LocalDateTime scheduled_date;
    private String location;
    private String status;
    private String notes;
    @ManyToOne
    @JoinColumn(name = "NurseID")
    private SchoolNurse nurse;


}
