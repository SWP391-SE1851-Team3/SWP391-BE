package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Entity
@Table
@Setter
@Getter
public class HealthConsentForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formID;

    @ManyToOne
    @JoinColumn(name = "studentID")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "health_ScheduleID")
    private HealthCheck_Schedule healthCheckSchedule;

    private Boolean isAgreed;
    private String notes;
}
