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
    private Integer studentID;
    private Integer gender;
    @NotBlank(message = "FullName Not allow empty")
    private String fullName;
    @NotBlank(message = "ClassName Not allow empty")
    private String className;
    private Integer isActive;
    private Integer parentID;

    @ManyToOne
    @JoinColumn(name = "ParentID", insertable = false, updatable = false)
    private Parent parent;
}


