package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "MedicalEvent_MedicalSupply")
public class MedicalEventMedicalSupply {
    @Id
    @ManyToOne
    @JoinColumn(name = "EventID")
    private MedicalEvent medicalEvent;

    @Id
    @ManyToOne
    @JoinColumn(name = "MedicalSupplyID")
    private MedicalSupply medicalSupply;

    @Column(name = "QuantityUsed")
    private Integer quantityUsed;

    public MedicalEventMedicalSupply() {
    }
    public MedicalEventMedicalSupply(MedicalEvent medicalEvent, MedicalSupply medicalSupply, Integer quantityUsed) {
        this.medicalEvent = medicalEvent;
        this.medicalSupply = medicalSupply;
        this.quantityUsed = quantityUsed;
    }
}
