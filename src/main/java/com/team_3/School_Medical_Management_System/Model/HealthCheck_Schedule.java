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
    private int health_ScheduleID;
    private Date schedule_Date;
    private String name;
    private String location;
    private String notes;
    private String status;

    // Added new fields for tracking creation and updates
    private Date create_at;
    private Date update_at;
    private Integer CreatedByNurseID;
    private Integer UpdatedByNurseID;

    // These fields are not stored in database, they're for transient use
    @Transient
    private String createdByNurseName;  // This will hold the name of the nurse who created

    @Transient
    private String updatedByNurseName;  // This will hold the name of the nurse who updated
}

