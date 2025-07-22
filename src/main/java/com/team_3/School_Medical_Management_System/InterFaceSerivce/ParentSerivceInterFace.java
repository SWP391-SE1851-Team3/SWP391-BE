package com.team_3.School_Medical_Management_System.InterFaceSerivce;

import com.team_3.School_Medical_Management_System.DTO.UserDTO;
import com.team_3.School_Medical_Management_System.Model.Parent;

import java.util.List;

public interface ParentSerivceInterFace {
    public List<Parent> getParents();
    public Parent getParentByName(String FullName);
    public void AddNewParent(Parent parent);
    public Parent UpdateParent(Parent parent);
    public void DeleteParent(int id);
    public Parent LoginByAccount(String Phone, String Password);
    public Parent GetParentById(int id);
    public boolean changePassword(String email, String oldPassword, String newPassword);

}
