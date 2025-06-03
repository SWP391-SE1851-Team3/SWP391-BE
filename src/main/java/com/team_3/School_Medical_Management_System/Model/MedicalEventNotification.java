package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "MedicalEventNotification")
@IdClass(MedicalEventNotificationId.class)
public class MedicalEventNotification {
    @Id
    @ManyToOne
    @JoinColumn(name = "ParentID")
    private Parent parent;

    @Id
    @ManyToOne
    @JoinColumn(name = "EventID")
    private MedicalEvent event;

    private String title;
    private String content;
}
