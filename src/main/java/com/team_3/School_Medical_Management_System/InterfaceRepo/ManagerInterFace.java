package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Manager;

public interface ManagerInterFace {

    public Manager findByEmailName (String username);
    public Manager LoginByAccount(String Email, String Password);

    public void saveManager(Manager manager);

}
