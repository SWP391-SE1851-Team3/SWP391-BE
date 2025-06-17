package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Consent_forms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consent_id ;
    @ManyToOne
    @JoinColumn(name = "StudentID")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "ParentID")
    private Parent parent;
    private Date consent_date;
    private int IsAgree;
    private String Reason;
    private String HasAllergy;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Vaccination_schedule schedule;
    @ManyToOne
    @JoinColumn(name = "Vaccine_id")
    private Vaccines vaccine;
}
