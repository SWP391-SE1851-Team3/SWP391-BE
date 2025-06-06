package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//Liên kết sự kiện với loại sự kiện y tee ok
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(MedicalEventTypeMappingId.class)
public class MedicalEventTypeMapping {
    @Id
    @ManyToOne
    @JoinColumn(name = "EventID")
    private MedicalEvent eventID;

    @Id
    @ManyToOne
    @JoinColumn(name = "EventTypeID")
    private MedicalEventType eventType;
}
