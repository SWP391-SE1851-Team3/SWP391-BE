package com.team_3.School_Medical_Management_System.Controller;
import com.team_3.School_Medical_Management_System.DTO.LoginResponse;
import com.team_3.School_Medical_Management_System.DTO.ManagerLoginResponeDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.ManagerServiceInterFace;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/managers")
@CrossOrigin(origins = "http://localhost:5173")
public class ManagerController {

    private ManagerServiceInterFace managerServiceInterFace;

    @Autowired
    public ManagerController(ManagerServiceInterFace managerServiceInterFace) {
        this.managerServiceInterFace = managerServiceInterFace;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody ManagerLoginResponeDTO loginRequest) {
        var manager = managerServiceInterFace.LoginByAccount(loginRequest.getEmail(), loginRequest.getPassword());
        if (manager != null) {
            // Trả về dữ liệu an toàn, không chứa mật khẩu, giu bao mat
            return ResponseEntity.ok(new LoginResponse(manager.getEmail(), 3, manager.getManagerID(),manager.getFullName()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password incorrect");
        }
    }
}

