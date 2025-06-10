package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Vaccine_batches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int batch_id;
    private String batch_name;

}
