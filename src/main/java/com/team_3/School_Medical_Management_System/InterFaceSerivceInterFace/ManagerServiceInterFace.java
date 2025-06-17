package com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace;

import com.team_3.School_Medical_Management_System.DTO.ManagerDTO;
import com.team_3.School_Medical_Management_System.DTO.UserDTO;
import com.team_3.School_Medical_Management_System.Model.Manager;
import org.springframework.http.ResponseEntity;

public interface ManagerServiceInterFace {
    public Manager LoginByAccount(String Email, String Password);
    public ManagerDTO createManager(ManagerDTO managerDTO);
    public ManagerDTO updateManager(int id, ManagerDTO managerDTO);
    public boolean deleteManager(int id);

    public ResponseEntity<?> createUser(UserDTO userDTO);

    public ResponseEntity<?> updateUser(int id, int roleId,UserDTO userDTO);

    public ResponseEntity<String> deleteUser(int id,  int roleId);
}
