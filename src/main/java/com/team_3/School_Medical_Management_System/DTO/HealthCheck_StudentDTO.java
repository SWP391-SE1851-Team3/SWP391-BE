package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Data
@NoArgsConstructor
@Getter
@Setter
public class HealthCheck_StudentDTO {
    private int checkID;
    private int studentID;
    private int health_ScheduleID; // Thêm ID lịch khám sức khỏe
    private String studentName;  // Thêm tên học sinh
    private String className;    // Thêm tên lớp
    private String status;       // Thêm trường status
    private float height;
    private float weight;
    private String visionLeft;
    private String visionRight;
    private String hearing;
    private String dentalCheck;
    private String temperature;
    private float bmi;
    private String overallResult;
    private int formID; // Added field for form ID

    // Added fields for tracking creation and updates
    private Integer CreatedByNurseID;
    private Integer UpdatedByNurseID;
    private Date create_at;
    private Date update_at;
    private String createdByNurseName;
    private String updatedByNurseName;
}
