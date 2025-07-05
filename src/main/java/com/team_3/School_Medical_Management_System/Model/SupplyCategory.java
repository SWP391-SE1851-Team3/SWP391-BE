package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "SupplyCategory")
@Data
public class SupplyCategory {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;
    @Column(name = "CategoryName")
    private String categoryName;
    @Column(name = "RequiresColdStorage")
    private String requiresColdStorage;
    @Column(name = "ExpiryDate")
    private Date expiryDate;

    public SupplyCategory(Long categoryID, String categoryName, String requiresColdStorage, Date expiryDate) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.requiresColdStorage = requiresColdStorage;
        this.expiryDate = expiryDate;
    }

    public SupplyCategory() {
    }
}
