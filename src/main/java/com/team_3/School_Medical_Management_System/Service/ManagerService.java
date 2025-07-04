package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.ManagerDTO;
import com.team_3.School_Medical_Management_System.DTO.UserDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.ManagerServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.ParentRepo;
import com.team_3.School_Medical_Management_System.Repositories.SchoolNurseRepo;
import com.team_3.School_Medical_Management_System.Repositories.StudentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManagerService implements ManagerServiceInterFace {
    private ManagerInterFace managerInterFace;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private SchoolNurseRepo nurseRepository;

    @Autowired
    private ParentRepo parentRepository;
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
    public ManagerDTO createManager(ManagerDTO managerDTO) {
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




    @Override
    public ResponseEntity<?> createUser(UserDTO userDTO) {

            Optional<Role> role = roleRepo.findById(userDTO.getRoleId());
            if (!role.isPresent()) {
                return ResponseEntity.badRequest().body("RoleID không tồn tại");
            }

            // Kiểm tra Username
            if (parentRepository.existsByUserName(userDTO.getUserName()) ||
                    nurseRepository.existsByUserName(userDTO.getUserName())) {
                return ResponseEntity.badRequest().body("Username đã tồn tại");
            }

            // Kiểm tra Email
            if (parentRepository.existsByEmail(userDTO.getEmail()) ||
                    nurseRepository.existsByEmail(userDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email đã tồn tại");
            }


            switch (userDTO.getRoleId()) {

                case 3: // Parent
                    Parent p = new Parent();
                    //  ParentDTO parentDTO = new ParentDTO();
                    p.setUserName(userDTO.getUserName());
                    p.setPassword(userDTO.getPassword());
                    p.setFullName(userDTO.getFullName());
                    p.setPhone(userDTO.getPhone());
                    p.setEmail(userDTO.getEmail());
                    p.setIsActive(userDTO.getIsActive());
                    p.setOccupation(userDTO.getOccupation());
                    p.setRelationship(userDTO.getRelationship());
                    p.setRoleID(userDTO.getRoleId());

                        parentRepository.AddNewParent(p);
                        return ResponseEntity.ok(p);


                case 2: // SchoolNurse
                    SchoolNurse n = new SchoolNurse();
                    n.setUserName(userDTO.getUserName());
                    n.setPassword(userDTO.getPassword());
                    n.setFullName(userDTO.getFullName());
                    n.setPhone(userDTO.getPhone());
                    n.setEmail(userDTO.getEmail());
                    n.setIsActive(userDTO.getIsActive());
                    n.setCertification(userDTO.getCertification());
                    n.setSpecialisation(userDTO.getSpecialisation());
                    n.setRoleID(userDTO.getRoleId());

                        nurseRepository.AddNewSchoolNurses(n);
                        return ResponseEntity.ok(n);

                default:
                    return ResponseEntity.badRequest().body("Không thể thêm mới tài khoản cho vai trò này!");
            }


    }

    @Override
    public ResponseEntity<?> updateUser(int id,int roleId, UserDTO userDTO) {

        switch (roleId) {
            case 3:
                Parent p = parentRepository.GetParentById(id);
                p.setUserName(userDTO.getUserName());
                p.setPassword(userDTO.getPassword());
                p.setFullName(userDTO.getFullName());
                p.setPhone(userDTO.getPhone());
                p.setEmail(userDTO.getEmail());
                p.setIsActive(userDTO.getIsActive());
                p.setOccupation(userDTO.getOccupation());
                p.setRelationship(userDTO.getRelationship());
                p.setRoleID(userDTO.getRoleId());
                Parent updateP = parentRepository.UpdateParent(p);
                return ResponseEntity.ok(updateP);

            case 2:
                SchoolNurse n = nurseRepository.GetSchoolNursesById(id);
                n.setUserName(userDTO.getUserName());
                n.setPassword(userDTO.getPassword());
                n.setFullName(userDTO.getFullName());
                n.setPhone(userDTO.getPhone());
                n.setEmail(userDTO.getEmail());
                n.setIsActive(userDTO.getIsActive());
                n.setCertification(userDTO.getCertification());
                n.setSpecialisation(userDTO.getSpecialisation());
                n.setRoleID(userDTO.getRoleId());
                SchoolNurse updateN = nurseRepository.UpdateSchoolNurses(n);
                return ResponseEntity.ok(updateN);
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteUser(int id, int roleId) {



        switch (roleId) {
            case 3: // Parent
                List<Student> students = studentRepo.getStudentsByParentID(id);
                for (Student s : students) {
                    students.remove(s);
                }
                parentRepository.DeleteParent(id);
                break;
            case 2: // SchoolNurse
                nurseRepository.DeleteSchoolNurses(id);
                break;

            default:
                return ResponseEntity.badRequest().body("RoleID không hợp lệ");
        }

        return ResponseEntity.notFound().build();


    }
//
}





































