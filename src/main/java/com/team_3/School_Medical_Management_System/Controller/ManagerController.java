package com.team_3.School_Medical_Management_System.Controller;
import com.team_3.School_Medical_Management_System.DTO.LoginResponse;
import com.team_3.School_Medical_Management_System.DTO.ManagerLoginResponeDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.ManagerServiceInterFace;
import io.jsonwebtoken.Claims;
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
  //  private final JwtUtil jwtUtil;

    @Autowired
    public ManagerController(ManagerServiceInterFace managerServiceInterFace) {
        this.managerServiceInterFace = managerServiceInterFace;
       // this.jwtUtil = jwtUtil;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody ManagerLoginResponeDTO loginRequest) {
        var manager = managerServiceInterFace.LoginByAccount(loginRequest.getEmail(), loginRequest.getPassword());
        if (manager != null) {
            String token = null;
//                    jwtUtil.generateToken(
//                    Integer.valueOf(manager.getManagerID()),  // hoặc nurseId / managerId nếu dùng chung login
//                    Integer.valueOf(manager.getRole().getRoleID()) // hoặc lấy theo logic role
//            );
            // Trả về dữ liệu an toàn, không chứa mật khẩu, giu bao mat
            return ResponseEntity.ok(new LoginResponse(manager.getEmail(),manager.getFullName(), token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password incorrect");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thiếu hoặc sai định dạng Authorization header");
            }

            String token = authHeader.substring(7);  // Bỏ "Bearer "
            Claims claims = null;
//                    jwtUtil.getClaimsFromToken(token);

            Integer role = claims.get("role", Integer.class);
            Integer userId = claims.get("userId", Integer.class);


            if (role == 3) {
                return ResponseEntity.ok("Xin chào Admin #" + userId);
            } else if (role == 2) {
                return ResponseEntity.ok("Xin chào Giáo viên #" + userId);
            } else {
                return ResponseEntity.ok("Xin chào Người dùng #" + userId);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ hoặc đã hết hạn");
        }
    }
}

