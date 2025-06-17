package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(Notifications_MedicalEventDetailsId.class)
public class Notifications_MedicalEventDetails {
    @Id
    @Column(name = "ParentID")
    private Integer parentId;

    @Id
    @Column(name = "EventID")
    private Integer eventId;

    @Column(name = "Title")
    private String title;

    @Column(name = "Content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "ParentID", insertable = false, updatable = false)
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "EventID", insertable = false, updatable = false)
    private MedicalEvent medicalEvent;
}
