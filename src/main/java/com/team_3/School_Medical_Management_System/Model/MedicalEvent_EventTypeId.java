package com.team_3.School_Medical_Management_System.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalEvent_EventTypeId {
    private Integer eventId;
    private Integer eventTypeId;

    public MedicalEvent_EventTypeId() {
    }
    public MedicalEvent_EventTypeId(Integer eventId, Integer eventTypeId) {
        this.eventId = eventId;
        this.eventTypeId = eventTypeId;
    }
}
