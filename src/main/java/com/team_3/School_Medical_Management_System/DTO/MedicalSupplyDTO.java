package com.team_3.School_Medical_Management_System.DTO;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MedicalSupplyDTO {
    private Integer MedicalSupplyID;
    private String SupplyName;
    private String Unit;
    private Integer QuantityAvailable;
    private Integer ReorderLevel;
    private String StorageTemperature;
    private Date DateAdded;
    private Integer VaccineTypeID;
    private Integer HealthCheckId;
    private  Integer CategoryID;

}
