package com.team_3.School_Medical_Management_System.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalEventNurseMappingId implements Serializable {
    private Integer nurse;
    private Integer event;
}
