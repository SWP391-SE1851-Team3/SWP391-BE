package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notifications_Parent")
@Getter
@Setter
public class NotificationsParent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId; // Khóa chính, tự động tăng

    @ManyToOne
    @JoinColumn(name = "ParentID")
    private Parent parent; // Quan hệ với bảng Parent

    @Column(name = "Title")
    private String title; // Tiêu đề thông báo

    @Column(name = "Content")
    private String content; // Nội dung thông báo

    @Column(name = "Status")
    private boolean status; // Trạng thái thông báo (đã đọc/chưa đọc)

    @Column(name = "Create_at")
    private LocalDateTime createAt;
}
