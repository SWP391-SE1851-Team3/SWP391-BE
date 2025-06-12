package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Parent;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;

import java.util.List;

public interface SchoolNurseInterFace {
    public List<SchoolNurse> getSchoolNurses();
    public SchoolNurse getSchoolNursesByName(String FullName);
    public void AddNewSchoolNurses(SchoolNurse schoolNurse);
    public SchoolNurse UpdateSchoolNurses(SchoolNurse schoolNurse);
    public void DeleteSchoolNurses(int id);
    public SchoolNurse LoginByAccount(String Email, String Password);
    public SchoolNurse GetSchoolNursesById(int id);
    public boolean changePassword(String email, String oldPassword, String newPassword);


    public boolean existsByUserName(String userName);
    public boolean existsByEmail(String email);
}
