package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.InterfaceRepo.RoleRepo;
import com.team_3.School_Medical_Management_System.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class RoleService {

    @Autowired
    private RoleRepo roleRepo;
    public RoleService(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }
}
