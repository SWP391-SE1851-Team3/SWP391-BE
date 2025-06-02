package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;

import com.team_3.School_Medical_Management_System.Model.Manager;

public interface ManagerServiceInterFace {
    public Manager LoginByAccount(String Email, String Password);
}
