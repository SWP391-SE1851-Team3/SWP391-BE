package com.team_3.School_Medical_Management_System.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class MedicalEventDTO {

//    private Integer eventId;
//    private String usageMethod;
//    private Boolean isEmergency;
//    private Boolean hasParentBeenInformed;
//    private String temperature;
//    private String heartRate;
//    private LocalDateTime eventDateTime;
//    private Integer parentId;
//    private Integer studentId;
//    private String eventType;
//    private String note;
//    private String result;
//    private String processingStatus;


    private Integer eventId; // Mã sự kiện y tế
    private String usageMethod; // Phương pháp sử dụng
    private Boolean isEmergency; // Tình trạng khẩn cấp
    private Boolean hasParentBeenInformed; // Đã thông báo phụ huynh
    private String temperature; // Nhiệt độ
    private String heartRate; // Nhịp tim
    @NotNull(message = "Thời gian sự kiện không được để trống")
    private LocalDateTime eventDateTime; // Thời gian sự kiện
    @NotNull(message = "ID phụ huynh không được để trống")
    private Integer parentId; // ID phụ huynh
    @NotNull(message = "ID học sinh không được để trống")
    private Integer studentId; // ID học sinh
    @NotEmpty(message = "Loại sự kiện không được để trống")
    private String eventType; // Loại sự kiện
    private String note; // Ghi chú
    private String result; // Kết quả
    private String processingStatus; //

}
