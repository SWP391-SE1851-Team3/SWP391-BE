package com.team_3.School_Medical_Management_System.DTO;


import com.team_3.School_Medical_Management_System.Model.Vaccines;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine_batchesDTO {
    private int batch_id;
    private String batch_number;
    private int quantity_received;
    private OffsetDateTime received_date;
    private int Vaccine_id;
}
