package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Data
public class MedicalEventUpdateDTO {
    private Integer eventId;
    private String usageMethod;
    private Boolean isEmergency;
    private Boolean hasParentBeenInformed;
    private String temperature;
    private String heartRate;
    private LocalDateTime eventDateTime;
     private Integer nurseId;
    private Integer studentId;
    private String note;
    private String result;
    private String processingStatus;
     // Y tá cập nhật sự kiện
    private String nurseName;

    private List<MedicalSupplyQuantityDTO> medicalSupplies;


    public MedicalEventUpdateDTO() {
    }
}
