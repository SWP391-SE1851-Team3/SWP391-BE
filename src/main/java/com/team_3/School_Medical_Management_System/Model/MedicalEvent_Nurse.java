package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@IdClass(MedicalEventNurseId.class)
public class MedicalEvent_Nurse {

    @Id

    @ManyToOne
    @JoinColumn(name = "EventID")
    private MedicalEvent medicalEvent;
    @Id
    @ManyToOne
    @JoinColumn(name = "NurseID")
    private SchoolNurse schoolNurse;


    public MedicalEvent_Nurse() {
    }

    public MedicalEvent_Nurse(MedicalEvent medicalEvent, SchoolNurse schoolNurse) {
        this.medicalEvent = medicalEvent;
        this.schoolNurse = schoolNurse;
    }
}
