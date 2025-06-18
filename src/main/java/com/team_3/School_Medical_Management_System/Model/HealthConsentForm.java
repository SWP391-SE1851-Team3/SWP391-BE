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
public class HealthConsentForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formID;
    private Date consentDate;
    private String consentLocation;
}
