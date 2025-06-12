package com.team_3.School_Medical_Management_System.Model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter

public class NotificationsMedicalEventDetailsId implements Serializable {
    private Integer parentId;
    private Integer eventId;

    public NotificationsMedicalEventDetailsId(Integer parentId, Integer eventId) {
        this.parentId = parentId;
        this.eventId = eventId;
    }
    public NotificationsMedicalEventDetailsId() {
    }
}
