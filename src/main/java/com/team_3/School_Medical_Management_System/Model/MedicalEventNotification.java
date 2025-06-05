package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
// Lưu thông báo gửi đến phụ huynh về sự kiện y tế
@IdClass(MedicalEventNotificationId.class)
public class MedicalEventNotification {
    @Id
    @ManyToOne
    @JoinColumn(name = "ParentID")
    private Parent parentID;

    @Id
    @ManyToOne
    @JoinColumn(name = "EventID")
    private MedicalEvent eventID;

    @Column(nullable = false)
    private String title; // Tiêu đề thông báo

    @Column(columnDefinition = "nvarchar(255)")
    private String content; // Nội dung thông báo
}
