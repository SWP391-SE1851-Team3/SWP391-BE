package com.team_3.School_Medical_Management_System.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.relational.core.sql.In;


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
    private Integer parentID;
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

    @NotBlank(message = "Occupation Not allow empty")
    private String occupation;
    @NotBlank(message = "Relationship Not allow empty")
    private String relationship;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "roleID", insertable = false, updatable = false)
    private Role role;




}
