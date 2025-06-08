package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class consent_forms {
    @Id
    private int consent_id ;
    private int  StudentID ;
    private int  ParentID ;
    private int schedule_id;
    private int Vaccine_id;
    private int RequirementParentConsent;
    private Date consent_date;
    private int IsAgree;
    private String Reason;
    private int HasAllergy;


}
