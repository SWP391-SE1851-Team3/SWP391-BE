package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
//sử dụng nó như một template để ánh xạ các thuộc tính chung vào các bảng con.
    @NotBlank(message = "Username không được để trống")
    @Column(unique = true)
    protected String userName;

    @NotBlank(message = "Password không được để trống")
    protected String password;

    @NotBlank(message = "FullName không được để trống")
    @Column(nullable = false)
    protected String fullName;

    @NotBlank(message = "Phone không được để trống")
    protected String phone;

    @NotBlank(message = "Email không được để trống")
    @Column(unique = true)
    protected String email;

    @Column(nullable = false)
    protected Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "RoleID", nullable = false)
    protected Role role;
}
