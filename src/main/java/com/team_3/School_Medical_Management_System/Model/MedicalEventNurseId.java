package com.team_3.School_Medical_Management_System.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalEventNurseId  {
    private MedicalEvent medicalEvent;
    private SchoolNurse schoolNurse;
    public MedicalEventNurseId() {
    }
    public MedicalEventNurseId(MedicalEvent medicalEvent, SchoolNurse schoolNurse) {
        this.medicalEvent = medicalEvent;
        this.schoolNurse = schoolNurse;
    }
}
