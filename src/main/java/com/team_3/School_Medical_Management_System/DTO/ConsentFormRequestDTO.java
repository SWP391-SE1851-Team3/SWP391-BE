package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class ConsentFormRequestDTO {
    private List<String> className;
    private int healthScheduleId;
    private Date sendDate;
    private Date expireDate;
    private String isAgreed;
    private String notes;
    private Integer createdByNurseId; // Add nurse ID who creates the consent form
    private Integer updatedByNurseID;
}
