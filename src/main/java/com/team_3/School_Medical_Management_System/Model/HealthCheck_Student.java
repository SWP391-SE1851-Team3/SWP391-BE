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
@Table
@Setter
@Getter
public class HealthCheck_Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int checkID;
    private Integer studentID;
    private Integer formID;
    private Integer health_ScheduleID;
    private Float height;
    private Float weight;
    private String visionLeft;
    private String visionRight;
    private String hearing;
    private String dentalCheck;
    private String temperature;
    private Float bmi;
    private String overallResult;
    // Added fields for tracking creation and updates
    private Integer CreatedByNurseID;
    private Integer UpdatedByNurseID;
    private Date create_at;
    private Date update_at;
}
