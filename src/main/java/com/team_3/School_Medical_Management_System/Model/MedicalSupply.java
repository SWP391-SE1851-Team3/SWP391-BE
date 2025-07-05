package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MedicalSupply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer MedicalSupplyID;
    private String SupplyName;
    private String Unit;
    private Integer QuantityAvailable;
    private Integer ReorderLevel;
    private String StorageTemperature;
    private Date DateAdded;
    @ManyToOne
    @JoinColumn(name = "CheckID")
    private HealthCheck_Student HealthCheck;


    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private SupplyCategory SupplyCategory;

    @ManyToOne
    @JoinColumn(name = "VaccineTypeID")
    private Vaccine_Types vaccineType;
}


