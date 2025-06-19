package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notifications_Parent")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class NotificationsParent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId; // Primary key, auto-increment

    @ManyToOne
    @JoinColumn(name = "ParentID")
    private Parent parent; // Relationship with Parent table

    @Column(name = "Title")
    private String title; // Notification title

    @Column(name = "Content")
    private String content; // Notification content

    @Column(name = "Status")
    private boolean status; // Notification status (read/unread)

    @Column(name = "Create_at")
    private LocalDateTime createAt;

    @Column(name = "Type")
    private String type; // Type of notification

    // Notification types from health check
    public static final String TYPE_HEALTH_CHECK_CONSENT = "HEALTH_CHECK_CONSENT";
    public static final String TYPE_HEALTH_CHECK_RESULT = "HEALTH_CHECK_RESULT";
    public static final String TYPE_HEALTH_CONSULTATION = "HEALTH_CONSULTATION";
}
