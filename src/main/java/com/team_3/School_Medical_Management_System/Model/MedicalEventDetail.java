package com.team_3.School_Medical_Management_System.Model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "MedicalEventDetail") // Uncomment if you want to specify a table name
public class MedicalEventDetail {

    @Id
    @ManyToOne
    @JoinColumn(name = "StudentID")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "EventID")
    private MedicalEvent event;

    private String note;
    private String result;
    private String processingStatus;
}
