package com.team_3.School_Medical_Management_System.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Entity
@Table
@Setter
@Getter
public class SchoolNurse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer nurseID;
    @NotBlank(message = "UserName Not allow empty")
    private String userName;
    @NotBlank(message = "Password Not allow empty")
    private String password;
    @NotBlank(message = "FullName Not allow empty")
    private String fullName;
    @Pattern(regexp = "^(84|0)(3|5|7|8|9)[0-9]{8}$", message = "Phone invalid")
    private String phone;
    @Email
    private String email;
    private int isActive;
    //    @JsonIgnore
    private int roleID;
    @NotBlank(message = "Certification Not allow empty")
    private String certification;
    @NotBlank(message = "Specialisation Not allow empty")
    private String specialisation;

// một role sẽ có nhiều nurse
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "roleID", insertable = false, updatable = false)
    private Role role;

}
