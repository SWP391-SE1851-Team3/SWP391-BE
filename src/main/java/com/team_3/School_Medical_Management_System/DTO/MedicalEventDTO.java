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
    private String typeName;
    private Integer nurseId;// Y tá tạo sự kiện
    private String nurseName; // Tên y tá tạo sự kiện
    private Integer updatedByNurseId; // Y tá cập nhật sự kiện
    private String updatedByNurseName;
    private String note;
    private String result;
    private String processingStatus;
    private Integer eventTypeId;

    private List<MedicalSupply> medicalSupplies; // Danh sách vật tư y tế liên quan đến sự kiện
    private int quantity;

    public MedicalEventDTO(Integer eventId, String usageMethod, boolean isEmergency, boolean hasParentBeenInformed, String temperature, String heartRate, LocalDateTime eventDateTime, Integer parentID, Integer studentId, String typeName, Integer nurseId, String nurseName, Integer updatedByNurseId, String updatedByNurseName, String note, String result, String processingStatus, Integer eventTypeId, List<MedicalSupply> medicalSupplies, int quantity) {
        this.eventId = eventId;
        this.usageMethod = usageMethod;
        this.isEmergency = isEmergency;
        this.hasParentBeenInformed = hasParentBeenInformed;
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.eventDateTime = eventDateTime;
        this.parentID = parentID;
        this.studentId = studentId;
        this.typeName = typeName;
        this.nurseId = nurseId;
        this.nurseName = nurseName;
        this.updatedByNurseId = updatedByNurseId;
        this.updatedByNurseName = updatedByNurseName;
        this.note = note;
        this.result = result;
        this.processingStatus = processingStatus;
        this.eventTypeId = eventTypeId;
        this.medicalSupplies = medicalSupplies;
        this.quantity = quantity;
    }

    public MedicalEventDTO() {
    }
}
