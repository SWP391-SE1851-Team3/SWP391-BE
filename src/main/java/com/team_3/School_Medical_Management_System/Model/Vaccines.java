package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private int Vaccine_id;
    private String Name;
    private String Manufacturer;
    private String Description;
    private String Recommended_ages;
    private int Doses_required;
    private String Created_at;
    private String Updated_at;

}
