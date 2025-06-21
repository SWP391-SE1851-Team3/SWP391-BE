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
public class HealthCheck_Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int health_scheduleID;
    private Date schedule_date;
    private String name;
    private String Location;
    private String notes;

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_APPROVED = "APPROVED";
    public static final String STATUS_REJECTED = "REJECTED";
}
