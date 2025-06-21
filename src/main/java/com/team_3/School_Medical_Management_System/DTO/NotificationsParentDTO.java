package com.team_3.School_Medical_Management_System.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotificationsParentDTO {
    private int notificationId; // ID thông báo
    private int parentId; // ID phụ huynh
    private String title; // Tiêu đề
    private String content; // Nội dung
    private boolean status; // Trạng thái
    private LocalDateTime createAt; // Thời gian tạo
    private String type; // Loại thông báo

    public NotificationsParentDTO() {
    }

    public NotificationsParentDTO(int notificationId, int parentId, String title, String content, boolean status, LocalDateTime createAt, String type) {
        this.notificationId = notificationId;
        this.parentId = parentId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.createAt = createAt;
        this.type = type;
    }
}
