package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;

@NoArgsConstructor
@ToString
@Entity
@Table(name = "HealthConsentForm")
@Setter
@Getter
public class HealthConsentForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formID;
    private int studentID;
    private int parentID;
    private int health_ScheduleID;
    private String isAgreed;
    private String notes;
    private Date send_date;
    private Date expire_date;
    private Integer createdByNurseID;
    private Integer updatedByNurseID;
}
