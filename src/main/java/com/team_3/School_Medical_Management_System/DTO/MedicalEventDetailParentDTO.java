package com.team_3.School_Medical_Management_System.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MedicalEventDetailParentDTO {
    private String usageMethod;
    private Boolean isEmergency;
    //private Boolean hasParentBeenInformed;
    private String temperature;
    private String heartRate;
    private LocalDateTime eventDateTime;
    private String note;
    private String result;
    private String processingStatus;

    private String fullName;
    private int gender;
    private String className;
    private List<String> eventTypeNames;
  //  private Integer createdByNurseId;
    private String createdByNurseName;
  // private Integer updatedByNurseId;
    private String updatedByNurseName;
// phuj huynh k am hiểu về thuốc lên có ther bỏ phần này
    //private List<MedicalSupplyQuantityDTO> listMedicalSupplies;


    public MedicalEventDetailParentDTO() {
    }



}
