package com.team_3.School_Medical_Management_System.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data

public class MedicalEventDetailId implements Serializable {
    private Integer studentID;
    private Integer eventID ;

    public MedicalEventDetailId() {
    }
    public MedicalEventDetailId(Integer  studentID, Integer eventID) {
        this.studentID = studentID;
        this.eventID = eventID;
    }
}
