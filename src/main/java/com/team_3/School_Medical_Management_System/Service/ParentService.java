package com.team_3.School_Medical_Management_System.Service;
import com.team_3.School_Medical_Management_System.DTO.UserDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.ParentSerivceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.RoleRepo;
import com.team_3.School_Medical_Management_System.Model.Parent;
import com.team_3.School_Medical_Management_System.Repositories.ParentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ParentService implements ParentSerivceInterFace {
    private ParentRepo parentRepo;


    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    public ParentService(ParentRepo parentRepo) {
        this.parentRepo = parentRepo;
    }

    @Override
    public List<Parent> getParents() {
        var p = parentRepo.getParents();
        if (p.isEmpty()) {
            throw new RuntimeException("No parent found");
        } else {
            return p;
        }
    }

    @Override
    public Parent getParentByName(String FullName) {
        var p = parentRepo.getParentByName(FullName);
        if (p.getFullName().length() < 6 || p.getFullName().length() > 15) {
            throw new RuntimeException("Parent name too short");
        }else if(p == null){
            throw new RuntimeException("Parent not found");
        }else {
            return p;
        }
    }

    @Override
    public void AddNewParent(Parent parent) {
        parentRepo.AddNewParent(parent);
    }
    @Override
    public Parent UpdateParent(Parent parent) {
        if (parent.getPhone().length() < 2|| parent.getPhone().length() > 11 ) {
            throw new RuntimeException("Phone Parent is empty");
        }
        if (parent.getEmail().isEmpty() || parent.getEmail().length() < 6) {
            throw new RuntimeException("Email Parent Invalid");
        }

        var p = parentRepo.GetParentById(parent.getParentID());
        if (p == null) {
            throw new RuntimeException("Parent not found with id: " + parent.getParentID());
        }
        p.setPhone(parent.getPhone());
        p.setEmail(parent.getEmail());
        p.setOccupation(parent.getOccupation());
        return parentRepo.UpdateParent(p);
    }


    @Override
    public void DeleteParent(int id) {
        parentRepo.DeleteParent(id);

    }


    @Override
    public Parent LoginByAccount(String Email, String Password) {
        var p = parentRepo.LoginByAccount(Email, Password);
        if (p.getEmail().length() < 6) {
            throw new RuntimeException("Email Parent too short");
        } else if (p.getPassword().isEmpty()){
            throw new RuntimeException("Password Parent is empty");

        }else {
            return p;
        }
    }

    @Override
    public Parent GetParentById(int id) {
        return parentRepo.GetParentById(id);
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
       var p = parentRepo.changePassword(email, oldPassword, newPassword);
       if(email == null || oldPassword == null || newPassword == null) {
           return false;
       }else if (email.length() < 5 || newPassword.length() < 5) {
           return false;
       }else {
           return p;
       }
    }

    @Override
    public UserDTO convertParentToUserDTO(Parent parent) {
        UserDTO dto = new UserDTO();
        //dto.setId(parent.getParentID());

        dto.setUserName(parent.getUserName());
        dto.setPassword(parent.getPassword());
        dto.setFullName(parent.getFullName());
        dto.setPhone(parent.getPhone());
        dto.setEmail(parent.getEmail());
        dto.setIsActive(parent.getIsActive());
        dto.setOccupation(parent.getOccupation());
        dto.setRelationship(parent.getRelationship());
        dto.setRoleId(parent.getRoleID());
        return dto;

    }

    @Override
    public Parent convertToParentEntity(UserDTO dto) {

        Parent parent = new Parent();
        parent.setUserName(dto.getUserName());
        parent.setPassword(dto.getPassword());
        parent.setFullName(dto.getFullName());
        parent.setPhone(dto.getPhone());
        parent.setEmail(dto.getEmail());
        parent.setIsActive(dto.getIsActive());
        parent.setOccupation(dto.getOccupation());
        parent.setRelationship(dto.getRelationship());
        parent.setRoleID(dto.getRoleId());
        return parent;
    }


}
