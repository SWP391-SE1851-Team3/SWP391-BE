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
    private int consultationID;

    @ManyToOne
    @JoinColumn(name = "studentID")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "checkID")
    private HealthCheck_Student healthCheckStudent;

    private String issue;
    private String recommendation;
    private Date scheduledDate;
    private boolean status; // false = pending, true = completed
    private String notes;
}
