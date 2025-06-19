package com.team_3.School_Medical_Management_System.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Entity
@Table
@Setter
@Getter
public class HealthConsultation_Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consultID;
    private int parentID;
}
