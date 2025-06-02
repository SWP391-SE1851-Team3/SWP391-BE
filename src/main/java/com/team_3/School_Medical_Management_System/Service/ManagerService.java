package com.team_3.School_Medical_Management_System.Service;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ManagerServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ManagerInterFace;
import com.team_3.School_Medical_Management_System.Model.Manager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ManagerService implements ManagerServiceInterFace {
    private ManagerInterFace managerInterFace;

    @Autowired
    public ManagerService(ManagerInterFace managerInterFace) {
        this.managerInterFace = managerInterFace;
    }

    @Override
    public Manager LoginByAccount(String Email, String Password) {
        var p = managerInterFace.LoginByAccount(Email, Password);
        if (p.getEmail().length() < 6) {
            throw new RuntimeException("Email Manager too short");
        } else if (p.getPassword().isEmpty()){
            throw new RuntimeException("Password Manager is empty");
        }else {
            return p;
        }
    }
}
