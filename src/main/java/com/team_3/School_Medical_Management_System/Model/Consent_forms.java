package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private Integer consent_id ;
    @ManyToOne
    @JoinColumn(name = "StudentID")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "ParentID")
    private Parent parent;
    private String IsAgree;
    private String Reason;
    private String HasAllergy;
    @ManyToOne
    @JoinColumn(name = "BatchID")
    private Vaccine_Batches vaccineBatches;
    private String status;
    private LocalDateTime send_date;
    private LocalDateTime expire_date;
}
