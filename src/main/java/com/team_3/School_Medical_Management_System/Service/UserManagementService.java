package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.UserDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ManagerRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ParentRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.RoleRepo;
import com.team_3.School_Medical_Management_System.InterfaceRepo.SchoolNurseRepository;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.ManagerRepo;
import com.team_3.School_Medical_Management_System.Repositories.ParentRepo;
import com.team_3.School_Medical_Management_System.Repositories.SchoolNurseRepo;
import com.team_3.School_Medical_Management_System.Repositories.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserManagementService {
    private StudentRepo studentRepo;
    private ManagerRepo managerRepo;
    private SchoolNurseRepo schoolNurseRepo;
    private ParentRepo parentRepo;
    private RoleRepo roleRepo;
    private SchoolNurseRepository schoolNurseRepository;
    private ParentRepository parentRepository;
    private ManagerRepository managerRepository;

    @Autowired
    public UserManagementService(StudentRepo studentRepo, ManagerRepo managerRepo, SchoolNurseRepo schoolNurseRepo,
                                 ParentRepo parentRepo, RoleRepo roleRepo, SchoolNurseRepository schoolNurseRepository,
                                 ParentRepository parentRepository, ManagerRepository managerRepository) {
        this.studentRepo = studentRepo;
        this.managerRepo = managerRepo;
        this.schoolNurseRepo = schoolNurseRepo;
        this.parentRepo = parentRepo;
        this.roleRepo = roleRepo;
        this.schoolNurseRepository = schoolNurseRepository;
        this.parentRepository = parentRepository;
        this.managerRepository = managerRepository;
    }

    // 1. Tạo người dùng
    public UserDTO createUserDTO(UserDTO dto) {
        if (existsByUserNameAndEmail(dto.getUserName(), dto.getEmail())) {
            throw new RuntimeException("Username hoặc Email đã tồn tại");
        }
        Role role = roleRepo.findById(dto.getRoleID())
                .orElseThrow(() -> new RuntimeException("Role không tồn tại với ID: " + dto.getRoleID()));
        User user = createUser(dto, role);

        saveUser(user);


        return convertToDTO(user);
    }


    // 2  . Lấy thông tin người dùng theo ID
    public UserDTO findUserByIdDTO(Integer id) {
        User user = findUserById(id);
        return convertToDTO(user);
    }

    //  3 .Update người dùng
    public UserDTO updateUserDTO(UserDTO dto, Integer id) {
        Role role = roleRepo.findById(dto.getRoleID())
                .orElseThrow(() -> new RuntimeException("Role không tồn tại với ID: " + dto.getRoleID()));
        User user = findUserById(id);
        if (existsByUserNameAndEmailExceptId(dto.getUserName(), dto.getEmail(), id)) {
            throw new RuntimeException("Username hoặc Email đã được sử dụng bởi người khác");
        }

        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setIsActive(dto.getIsActive());
      //  user.setRole(role);
        if(user instanceof  SchoolNurse){
            ((SchoolNurse) user).setCertification(dto.getCertification());
            ((SchoolNurse) user).setSpecialisation(dto.getSpecialisation());
        } else if (user instanceof Parent) {
            ((Parent) user).setOccupation(dto.getOccupation());
            ((Parent) user).setRelationship(dto.getRelationship());
        }
         saveUser(user);
        return convertToDTO(user);
         // Chưa hoàn thành
    }

    // 4. Xoá người dùng
    public  void deleteUserDTO(Integer id) {
        User user = findUserById(id);
        deleteUser(user);
    }




    // 5

private User deleteUser(User user) {
        if (user instanceof Parent) {
            parentRepository.delete((Parent) user);
        } else if (user instanceof SchoolNurse) {
            schoolNurseRepository.delete((SchoolNurse) user);
        } else if (user instanceof Manager) {
            managerRepository.delete((Manager) user);
        } else {
            throw new RuntimeException("Loại người dùng không hợp lệ");
        }
        return user;
    }

    private boolean existsByUserNameAndEmailExceptId(String userName, String email, Integer id) {
        return (parentRepo.existsByUserName(userName) && !parentRepository.existsById(id)) ||
                (schoolNurseRepo.existsByUserName(userName) && !schoolNurseRepository.existsById(id)) ||
                (managerRepo.existsByUserName(userName) && !managerRepository.existsById(id)) ||
                (parentRepo.existsByEmail(email) && !parentRepository.existsById(id)) ||
                (schoolNurseRepo.existsByEmail(email) && !schoolNurseRepository.existsById(id)) ||
                (managerRepo.existsByEmail(email) && !managerRepository.existsById(id));
    }


    private void saveUser(User user) {
        if (user instanceof Parent) {
            parentRepo.AddNewParent((Parent) user);
        } else if (user instanceof SchoolNurse) {
            schoolNurseRepo.AddNewSchoolNurses((SchoolNurse) user);
        } else if (user instanceof Manager) {
            managerRepo.addNewManager((Manager) user);
        } else {
            throw new RuntimeException("Loại người dùng không hợp lệ");
        }

    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserName());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setIsActive(user.getIsActive());
       // dto.setRoleID(user.getRole().getRoleID());

        if (user instanceof Parent) {
            Parent parent = (Parent) user;
            dto.setOccupation(parent.getOccupation());
            dto.setRelationship(parent.getRelationship());
        } else if (user instanceof SchoolNurse) {
            SchoolNurse schoolNurse = (SchoolNurse) user;
            dto.setCertification(schoolNurse.getCertification());
            dto.setSpecialisation(schoolNurse.getSpecialisation());
        }
        return dto;
    }

    private boolean existsByUserNameAndEmail(String userName, String email) {

        return parentRepo.existsByEmail(email) ||
                parentRepo.existsByUserName(userName) ||
                managerRepo.existsByEmail(email) ||
                managerRepo.existsByUserName(userName) ||
                schoolNurseRepo.existsByEmail(email) ||
                schoolNurseRepo.existsByUserName(userName);
    }

    private User createUser(UserDTO dto, Role role) {
        User user;

        if (role.getRoleName().equals("Phụ huynh")) {
            user = new Parent();

            ((Parent) user).setOccupation(dto.getOccupation());
            ((Parent) user).setRelationship(dto.getRelationship());
        } else if (role.getRoleName().equals("Y tá")) {
            user = new SchoolNurse();


            ((SchoolNurse) user).setCertification(dto.getCertification());
            ((SchoolNurse) user).setSpecialisation(dto.getSpecialisation());
        } else if (role.getRoleName().equals("Quản trị viên")) {
            user = new Manager();
        } else {
            throw new RuntimeException("Vai trò không hợp lệ");
        }
        user.setUserName(dto.getUserName());
        user.setPassword("default_password"); // Cần mã hóa thực tế
        user.setFullName(dto.getFullName());
        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());
        user.setIsActive(dto.getIsActive());
       // user.setRole(role);
        return user;
    }


    private User findUserById(Integer id) {
        Optional<Parent> parent = parentRepository.findById(id);
        if (parent.isPresent()) return parent.get();

        Optional<SchoolNurse> nurse = schoolNurseRepository.findById(id);
        if (nurse.isPresent()) return nurse.get();

        Optional<Manager> manager = managerRepository.findById(id);
        if (manager.isPresent()) return manager.get();

        throw new RuntimeException("Người dùng không tồn tại với ID: " + id);
    }




}
