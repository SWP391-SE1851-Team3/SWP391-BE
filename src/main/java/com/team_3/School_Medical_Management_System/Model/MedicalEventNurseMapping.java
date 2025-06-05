package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.*;

// Liên kết giữa sự kiện y tế và y tá trường học@
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(MedicalEventNurseMappingId.class)
public class MedicalEventNurseMapping {
    @Id
    @ManyToOne
    @JoinColumn(name = "NurseID")
    private SchoolNurse nurseID;

    @Id
    @ManyToOne
    @JoinColumn(name = "EventID")
    private MedicalEvent eventID;
}
