package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
// Luư thông tin của sự kiện y tế vào class này
@Table(name = "MedicalEvent")
public class MedicalEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventID;
    @NotBlank(message = "Phương pháp xử lí không được để trống")
    private String usageMethod;
    @NotNull(message = "Tình trạng khẩn cấp không được để trống")
    private Boolean isEmergency;
    @NotNull(message = "Thông báo phụ huynh không được để trống")
    private Boolean hasParentBeenInformed;
    @NotBlank(message = "Nhiệt độ không được để trống")
    @Pattern(regexp = "^\\d+(\\.\\d+)?°C$", message = "Nhiệt độ phải có định dạng số và kết thúc bằng ' °C'")
    private String temperature;
    @NotBlank(message = "Nhịp tim không được để trống")
  @Pattern(regexp = "^\\d+\\s?bpm$", message = "Nhịp tim phải có định dạng số và kết thúc bằng 'bpm'")
    private String heartRate;
    @NotNull(message = "Thời gian sự kiện không được để trống")
    private LocalDateTime eventDateTime;


    @ManyToOne
    @JoinColumn(name = "ParentID")
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "CreatedByNurseID")
    private SchoolNurse createdByNurse;

    @ManyToOne
    @JoinColumn(name = "UpdatedByNurseID")
    private SchoolNurse updatedByNurse;


    @OneToMany(mappedBy = "medicalEvent", cascade = CascadeType.ALL)
    private List<MedicalEvent_EventType> medicalEventEventTypes = new ArrayList<>();




    @OneToMany(mappedBy = "medicalEvent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalEventMedicalSupply> medicalEventMedicalSupplies = new ArrayList<>();


    public MedicalEvent() {
    }




    public MedicalEvent(String usageMethod, Boolean isEmergency, Boolean hasParentBeenInformed, String temperature, String heartRate, LocalDateTime eventDateTime) {
        this.usageMethod = usageMethod;
        this.isEmergency = isEmergency;
        this.hasParentBeenInformed = hasParentBeenInformed;
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.eventDateTime = eventDateTime;

    }

    public void addMedicalSupply(MedicalSupply medicalSupply, Integer quantityUsed) {
        MedicalEventMedicalSupply link = new MedicalEventMedicalSupply(this, medicalSupply, quantityUsed);
        medicalEventMedicalSupplies.add(link);
    }

}
