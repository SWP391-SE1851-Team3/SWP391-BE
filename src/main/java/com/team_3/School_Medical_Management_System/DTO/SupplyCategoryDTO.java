package com.team_3.School_Medical_Management_System.DTO;

import lombok.Data;

@Data


public class SupplyCategoryDTO {
    private Long id;
    private  String categoryName;

    public SupplyCategoryDTO(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public SupplyCategoryDTO() {
    }
    public SupplyCategoryDTO(String categoryName) {
        this.categoryName = categoryName;
    }
}
