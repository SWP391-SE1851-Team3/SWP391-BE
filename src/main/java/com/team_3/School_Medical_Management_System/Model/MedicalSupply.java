package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Integer MedicalSupplyID;
    private String SupplyName;
    private String Unit;
    private Integer QuantityAvailable;
    private Integer ReorderLevel;
    private String StorageTemperature;
    private Date DateAdded;
    @ManyToOne

    @JoinColumn(name = "CheckID")
    private HealthCheck HealthCheck;

    @ManyToOne
    @JoinColumn(name = "EventID")
    private MedicalEvent MedicalEvent;

    @ManyToOne
    @JoinColumn(name = "BatchID")
    private Vaccine_Batches vaccineBatch;
    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private SupplyCategory SupplyCategory;
}


