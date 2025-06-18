package com.team_3.School_Medical_Management_System.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
@NoArgsConstructor
@ToString
@Entity
@Table
@Setter
@Getter
public class Parent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int ParentID;
    @NotBlank(message = "UserName Not allow empty")
    private String UserName;
    @NotBlank(message = "Password Not allow empty")
    private String Password;
    @NotBlank(message = "FullName Not allow empty")
    private String FullName;
    @Pattern(regexp = "^(84|0)(3|5|7|8|9)[0-9]{8}$", message = "Phone invalid")
    private String Phone;
    @Email
    private String Email;
    private int IsActive;
    private int RoleID;
    @NotBlank(message = "Occupation Not allow empty")
    private String Occupation;
    @NotBlank(message = "Relationship Not allow empty")
    private String Relationship;

}
