package com.team_3.School_Medical_Management_System.Model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class MedicalEventDetailsId implements Serializable {
    private Integer student;
    private Integer medicalEvent;

    public MedicalEventDetailsId() {
    }
    public MedicalEventDetailsId(Integer  studentID, Integer eventID) {
        this.student = studentID;
        this.medicalEvent = eventID;
    }
}
