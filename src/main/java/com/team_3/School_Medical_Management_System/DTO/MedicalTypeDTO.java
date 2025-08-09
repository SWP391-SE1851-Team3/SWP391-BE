package com.team_3.School_Medical_Management_System.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalTypeDTO {
    private Integer eventTypeId;
    private String typeName;

    public MedicalTypeDTO() {
    }
    public MedicalTypeDTO(Integer eventTypeId, String typeName) {
        this.eventTypeId = eventTypeId;
        this.typeName = typeName;
    }

}
