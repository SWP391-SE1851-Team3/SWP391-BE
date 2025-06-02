package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.SchoolNurseServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.SchoolNurseInterFace;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional

public class SchoolNurseService implements SchoolNurseServiceInterFace {

    private SchoolNurseInterFace schoolNurseInterFace;

    @Autowired
    public SchoolNurseService(SchoolNurseInterFace schoolNurseInterFace) {
        this.schoolNurseInterFace = schoolNurseInterFace;

    }

    @Override
    public List<SchoolNurse> getSchoolNurses() {
        return schoolNurseInterFace.getSchoolNurses();
    }

    @Override
    public SchoolNurse getSchoolNursesByName(String FullName) {
        var p = schoolNurseInterFace.getSchoolNursesByName(FullName);
        if (p == null) {
            throw new RuntimeException("School Nurse with FullName " + FullName + " not found");
        } else if (p.getFullName().length() < 5) {
            throw new RuntimeException("School Nurse with FullName " + FullName + " is too short");
        } else {
            return p;
        }
    }

    @Override
    public void AddNewSchoolNurses(SchoolNurse schoolNurse) {
        var p = schoolNurseInterFace.GetSchoolNursesById(schoolNurse.getNurseID());
        if (p == null) {
            schoolNurseInterFace.AddNewSchoolNurses(schoolNurse);
        } else {
            throw new RuntimeException("schoolNurse Already Exists");
        }
    }

    @Override
    public SchoolNurse UpdateSchoolNurses(SchoolNurse schoolNurse) {
        if (schoolNurse.getPhone() == null || schoolNurse.getPhone().isEmpty()) {
            throw new RuntimeException(" Phone School Nurse isn't null or empty");
        } else if (schoolNurse.getEmail().length() < 5) {
            throw new RuntimeException("Email School Nurse is invalid");
        } else {
            var p = schoolNurseInterFace.GetSchoolNursesById(schoolNurse.getNurseID());
            if (p == null) {
                throw new RuntimeException("Parent doesn't exit: " + schoolNurse.getNurseID());
            } else {
                p.setPhone(schoolNurse.getPhone());
                p.setEmail(schoolNurse.getEmail());
                p.setCertification(schoolNurse.getCertification());
                p.setSpecialisation(schoolNurse.getSpecialisation());
                return schoolNurseInterFace.UpdateSchoolNurses(p);


            }
        }
    }

    @Override
    public void DeleteSchoolNurses(int id) {
        var p = schoolNurseInterFace.GetSchoolNursesById(id);
        if (p == null) {
            throw new RuntimeException("School Nurse with id " + id + " not found");
        } else {
            schoolNurseInterFace.DeleteSchoolNurses(id);
        }
    }

    @Override
    public SchoolNurse LoginByAccount(String Email, String Password) {
        var p = schoolNurseInterFace.LoginByAccount(Email, Password);
        if (p == null) {
            throw new RuntimeException("School Nurse with Email " + Email + " not found");
        } else {
            return p;
        }
    }

    @Override
    public SchoolNurse GetSchoolNursesById(int id) {
       var p = schoolNurseInterFace.GetSchoolNursesById(id);
       if (p == null) {
           throw new RuntimeException("School Nurse with id " + id + " not found");
       }else {
           return p;
       }
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        var p = schoolNurseInterFace.changePassword(email, oldPassword, newPassword);
        if (email == null || oldPassword == null || newPassword == null) {
            return false;
        } else if (email.length() < 5 || newPassword.length() < 5) {
            return false;
        } else {
            return p;
        }
    }

}
