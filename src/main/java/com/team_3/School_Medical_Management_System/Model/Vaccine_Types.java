package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Vaccine_Types {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer VaccineTypeID;
    private String Name;
    private String Manufacturer;
    private String Description;
    private String Recommended_ages;
    private LocalDate Created_at;
    private LocalDate Updated_at;

}
