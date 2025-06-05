package com.team_3.School_Medical_Management_System.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalEventNotificationId implements Serializable {
    private Integer parentID;
    private Integer eventID;

    // dùng vậy thì JPA mới hiểu để sử dụng 2 khóa chính này cho việc tìm kiếm hoặc bất cứ việc gì đc
    // nếu không có thì sẽ báo lỗi không tìm thấy khóa chính

}
