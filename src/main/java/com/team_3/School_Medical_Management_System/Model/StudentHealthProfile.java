package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class StudentHealthProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ProfileID;
    private String AllergyDetails;
    private String ChronicDiseases;
    private String TreatmentHistory;
    private String VisionLeft;
    private String VisionRight;
    private String Hearings_Score;
    private double Height;
    private double Weight;
    private String NoteOfParent;
    private Date LastUpdated;
    private  int StudentID;
    private int ParentID;
}
