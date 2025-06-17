package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.OffsetDateTime;

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
    private String batch_number;
    private int quantity_received;
    private OffsetDateTime received_date;
    @ManyToOne
    @JoinColumn(name = "Vaccine_id")
    private Vaccines vaccine;
}
