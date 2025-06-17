package com.team_3.School_Medical_Management_System.Model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter

public class Notifications_MedicalEventDetailsId implements Serializable {
    private Integer parentId;
    private Integer eventId;

    public Notifications_MedicalEventDetailsId(Integer parentId, Integer eventId) {
        this.parentId = parentId;
        this.eventId = eventId;
    }
    public Notifications_MedicalEventDetailsId() {
    }
}
