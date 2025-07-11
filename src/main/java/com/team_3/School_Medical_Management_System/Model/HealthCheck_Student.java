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
public class HealthCheck_Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int checkID;
    private int studentID;
    private float height;
    private float weight;
    private String visionLeft;
    private String visionRight;
    private String hearing;
    private String dentalCheck;
    private float temperature;
    private float bmi;

    @ManyToOne
    @JoinColumn(name = "health_ScheduleID")
    private HealthCheck_Schedule healthCheckSchedule;
}
