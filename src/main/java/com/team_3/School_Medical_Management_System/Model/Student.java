package com.team_3.School_Medical_Management_System.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int StudentID;
    private int Gender;
    @NotBlank(message = "FullName Not allow empty")
    private String FullName;
    @NotBlank(message = "ClassName Not allow empty")
    private String ClassName;
    private int IsActive;
    private int ParentID;

}

