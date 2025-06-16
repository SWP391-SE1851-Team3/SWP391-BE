package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.ManagerDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ManagerServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ManagerInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ManagerRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.RoleRepo;
import com.team_3.School_Medical_Management_System.Model.Manager;
import com.team_3.School_Medical_Management_System.Model.Role;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ManagerService implements ManagerServiceInterFace {
    private ManagerInterFace managerInterFace;
    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    public ManagerService(ManagerInterFace managerInterFace) {
        this.managerInterFace = managerInterFace;
    }

    @Override
    public Manager LoginByAccount(String Email, String Password) {
        var p = managerInterFace.LoginByAccount(Email, Password);
        if (p.getEmail().length() < 6) {
            throw new RuntimeException("Email Manager too short");
        } else if (p.getPassword().isEmpty()) {
            throw new RuntimeException("Password Manager is empty");
        } else {
            return p;
        }
    }

    @Override
    public ManagerDTO createManager(ManagerDTO managerDTO ) {
    Manager manager = new Manager();
        Role role = roleRepo.findById(managerDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + managerDTO.getRoleId()));
        manager.setUserName(managerDTO.getUsername());
        manager.setPassword(managerDTO.getPassword());
        manager.setFullName(managerDTO.getFullName());
        manager.setPhone(managerDTO.getPhone());
        manager.setEmail(managerDTO.getEmail());
        manager.setIsActive(managerDTO.getIsActive());
        manager.setRole(role);

        Manager savedManager = managerRepository.save(manager);
        managerDTO.setManagerId(savedManager.getManagerID());
        return managerDTO;

    }

    @Override
    public ManagerDTO updateManager(int id, ManagerDTO managerDTO) {
        Optional<Manager> managerOptional = managerRepository.findById(id);
        Role role = roleRepo.findById(managerDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + managerDTO.getRoleId()));
        if (managerOptional.isPresent()) {
            Manager manager = managerOptional.get();
            manager.setUserName(managerDTO.getUsername());
            if (managerDTO.getPassword() != null && !managerDTO.getPassword().isEmpty()) {
                manager.setPassword(managerDTO.getPassword());
            }
            manager.setFullName(managerDTO.getFullName());
            manager.setPhone(managerDTO.getPhone());
            manager.setEmail(managerDTO.getEmail());
            manager.setIsActive(managerDTO.getIsActive());
            manager.setRole(role);

            Manager updatedManager = managerRepository.save(manager);
            managerDTO.setManagerId(updatedManager.getManagerID());
            return managerDTO;
        }
        return null;

    }

    @Override
    public boolean deleteManager(int id) {
        if (managerRepository.existsById(id)) {
            managerRepository.deleteById(id);
            return true;
        }
        return false;

    }


}
