package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
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
    // @Pattern(regexp = "^(3[5-9]|[4-4][0-9]|50)(\\.\\d)?°C$", message = "Giá trị không hợp lệ. Vui lòng nhập giá trị từ 35 đến 50 độ C.")

    private String temperature;
    //@Pattern(regexp = "^\\d{2,3}(\\s*(?:bpm|BPM))?$", message = "Nhịp tim không hợp lệ (ví dụ: 60, 75 bpm, 80BPM). Cho phép từ 60 đến 999 bpm.")
    private String heartRate;
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

    private List<MedicalEvent_EventType> medicalEventEventTypes;

    @OneToMany(mappedBy = "medicalEvent", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<MedicalSupply> medicalSupplies;

    public MedicalEvent() {
    }
     public  void add(MedicalSupply o){

        medicalSupplies.add(o);
     }

    public MedicalEvent(String usageMethod, Boolean isEmergency, Boolean hasParentBeenInformed, String temperature, String heartRate, LocalDateTime eventDateTime) {
        this.usageMethod = usageMethod;
        this.isEmergency = isEmergency;
        this.hasParentBeenInformed = hasParentBeenInformed;
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.eventDateTime = eventDateTime;

    }

}
