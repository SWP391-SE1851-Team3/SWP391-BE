package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Parent;

import java.util.List;

public interface ParentInterFace {
    public List<Parent> getParents();
    public Parent getParentByName(String FullName);
    public void AddNewParent(Parent parent);
    public Parent UpdateParent(Parent parent);
    public void DeleteParent(int id);
    public Parent LoginByAccount(String Email, String Password);
    public Parent GetParentById(int id);
    public boolean changePassword(String email, String oldPassword, String newPassword);
    public Parent getParentByEmail(String Email);

}
