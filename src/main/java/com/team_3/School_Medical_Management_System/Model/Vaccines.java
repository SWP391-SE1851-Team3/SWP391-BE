package com.team_3.School_Medical_Management_System.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    private int Doses_required;
    private String Created_at;
    private String Updated_at;
}
