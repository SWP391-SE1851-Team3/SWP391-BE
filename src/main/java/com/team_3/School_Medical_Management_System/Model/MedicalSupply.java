package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
public class MedicalSupply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer medicalSupplyId;

    @Column(name = "SupplyName")
    private String supplyName;

    @Column(name = "Unit")
    private String unit;

    @Column(name = "QuantityAvailable")
    private Integer quantityAvailable;

    @Column(name = "ReorderLevel")
    private Integer reorderLevel;

    @Column(name = "StorageTemperature")
    private String storageTemperature;

    @Column(name = "DateAdded")
    private java.sql.Date dateAdded;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private SupplyCategory category;


    @ManyToOne
    @JoinColumn(name = "BatchID")
    private Vaccine_Batches vaccineBatch;
    @ManyToOne
    @JoinColumn(name = "EventID")
    private MedicalEvent medicalEvent;

    @ManyToOne
    @JoinColumn(name = "CheckID")
    private HealthCheck_Student healthCheckStudent;

    // Self-referential relationship for hierarchical structure
    public MedicalSupply() {
    }

    public MedicalSupply(Integer medicalSupplyId, String supplyName, String unit, Integer quantityAvailable, Integer reorderLevel, String storageTemperature, Date dateAdded, SupplyCategory category, Vaccine_Batches vaccineBatch, MedicalEvent medicalEvent, HealthCheck_Student healthCheckStudent) {
        this.medicalSupplyId = medicalSupplyId;
        this.supplyName = supplyName;
        this.unit = unit;
        this.quantityAvailable = quantityAvailable;
        this.reorderLevel = reorderLevel;
        this.storageTemperature = storageTemperature;
        this.dateAdded = dateAdded;
        this.category = category;
        this.vaccineBatch = vaccineBatch;
        this.medicalEvent = medicalEvent;
        this.healthCheckStudent = healthCheckStudent;
    }
}
