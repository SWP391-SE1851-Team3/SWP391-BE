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
public class HealthConsultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consultID;
    private int studentID;
    private int  checkID;
    private String status; // false = pending, true = completed
    private String reason;

    // Added fields for tracking creation and updates
    private Integer CreatedByNurseID;
    private Integer UpdatedByNurseID;
    private Date create_at;
    private Date update_at;

}
