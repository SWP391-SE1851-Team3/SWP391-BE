package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@IdClass(MedicalEvent_EventTypeId.class)
public class MedicalEvent_EventType {
    @Id
    @Column(name = "eventId")
    private Integer eventId; // Phải có thuộc tính này để khớp với @IdClass

    @Id
    @Column(name = "eventTypeId")
    private Integer eventTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId", insertable = false, updatable = false) // Tránh xung đột với @Id
    private MedicalEvent medicalEvent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventTypeId", insertable = false, updatable = false)
    private MedicalEventType eventType;

    public MedicalEvent_EventType() {
    }

    public MedicalEvent_EventType(Integer eventId, Integer eventTypeId) {
        this.eventId = eventId; // Đổi thành eventId
        this.eventTypeId = eventTypeId;
    }
}
