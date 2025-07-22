package com.team_3.School_Medical_Management_System.DTO;

import com.team_3.School_Medical_Management_System.Model.MedicalSupply;
import com.team_3.School_Medical_Management_System.Model.Student;
import lombok.*;
import org.aspectj.weaver.loadtime.definition.LightXMLParser;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MedicalEventDTO {
    private Integer eventId;
    private String usageMethod;
    private boolean isEmergency;
    private boolean hasParentBeenInformed;
    private String temperature;
    private String heartRate;
    private LocalDateTime eventDateTime;
    private Integer parentID;
    private Integer studentId;
    // private String typeName;
    private Integer nurseId;// Y tá tạo sự kiện
    private String nurseName; // Tên y tá tạo sự kiện
    private Integer updatedByNurseId; // Y tá cập nhật sự kiện
    private String updatedByNurseName;
    private String note;
    private String result;
    private String processingStatus;
    // private Integer eventTypeId;
    private List<MedicalTypeDTO> listMedicalEventTypes;
    private List<MedicalSupplyQuantityDTO> medicalSupplies;


    public MedicalEventDTO() {
    }
}
