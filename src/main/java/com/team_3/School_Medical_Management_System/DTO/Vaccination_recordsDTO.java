package com.team_3.School_Medical_Management_System.DTO;

import com.team_3.School_Medical_Management_System.Model.Student;
import com.team_3.School_Medical_Management_System.Model.Vaccination_schedule;
import com.team_3.School_Medical_Management_System.Model.Vaccine_batches;
import com.team_3.School_Medical_Management_System.Model.Vaccines;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Vaccination_recordsDTO {
    private int VaccinationRecordID;
    private String notes;
    private String  Vaccine_Name;
    private String Student_Name;
    private int schedule_id;
    private int batch_id;

}
