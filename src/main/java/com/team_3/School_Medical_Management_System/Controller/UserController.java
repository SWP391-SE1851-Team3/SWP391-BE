package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.SchoolNurseDTO;
import com.team_3.School_Medical_Management_System.DTO.UserDTO;
import com.team_3.School_Medical_Management_System.DTO.UserUpdateDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ParentRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.SchoolNurseRepository;
import com.team_3.School_Medical_Management_System.Model.Role;
import com.team_3.School_Medical_Management_System.Service.ManagerService;
import com.team_3.School_Medical_Management_System.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private ManagerService managerService;

    private ParentRepository parentRepository;
    private SchoolNurseRepository schoolNurseRepository;

    @Autowired
    public UserController(ParentRepository parentRepository, SchoolNurseRepository schoolNurseRepository) {
        this.parentRepository = parentRepository;
        this.schoolNurseRepository = schoolNurseRepository;
    }


    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
            return ResponseEntity.ok(managerService.createUser(userDTO));

    }




    @PutMapping("/updateUser/{id}/{roleId}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @PathVariable int roleId, @RequestBody UserUpdateDTO userDTO) {

        return ResponseEntity.ok(managerService.updateUser(id,roleId,userDTO));
    }


    @DeleteMapping("/deleteUser/{id}/{roleId}")
    public ResponseEntity<?> deleteUser(@PathVariable int id, @PathVariable int roleId) {
        return ResponseEntity.ok(managerService.deleteUser(id, roleId));
    }



    @GetMapping("/getAllRole")
    public  ResponseEntity<List<Role>> getAllRole() {
        List<Role> roles = new ArrayList<>();

            roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }
}