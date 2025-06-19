package com.team_3.School_Medical_Management_System.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team_3.School_Medical_Management_System.Enum.ConsentFormStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
    private Integer consent_id ;
    @ManyToOne
    @JoinColumn(name = "StudentID")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "ParentID")
    private Parent parent;
    private Integer IsAgree;
    private String Reason;
    private String HasAllergy;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Vaccination_schedule schedule;
    @ManyToOne
    @JoinColumn(name = "Vaccine_id")
    private Vaccines vaccine;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ConsentFormStatus status;
    private LocalDateTime send_date;
    private LocalDateTime expire_date;
}
