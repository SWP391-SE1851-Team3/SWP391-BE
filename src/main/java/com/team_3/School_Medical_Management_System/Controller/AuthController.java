package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.JwtResponse;
import com.team_3.School_Medical_Management_System.DTO.LoginRequest;
import com.team_3.School_Medical_Management_System.DTO.SignUpRequest;
import com.team_3.School_Medical_Management_System.InterfaceRepo.RoleRepo;
import com.team_3.School_Medical_Management_System.InterfaceRepo.SchoolNurseRepository;
import com.team_3.School_Medical_Management_System.Model.Manager;
import com.team_3.School_Medical_Management_System.Model.Role;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import com.team_3.School_Medical_Management_System.Repositories.ManagerRepo;
import com.team_3.School_Medical_Management_System.Repositories.ParentRepo;
import com.team_3.School_Medical_Management_System.Repositories.SchoolNurseRepo;
import com.team_3.School_Medical_Management_System.security.JwtUtils;
import com.team_3.School_Medical_Management_System.security.UserDetailsImpl;
import com.team_3.School_Medical_Management_System.security.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserDetailsServiceImpl userDetailsService;
    private JwtUtils jwtUtils;
    private SchoolNurseRepository nurseRepo;
    private SchoolNurseRepo schoolNurseRepo;
    private ManagerRepo adminRepository;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserDetailsServiceImpl userDetailsService, JwtUtils jwtUtils,
                          SchoolNurseRepository nurseRepo, SchoolNurseRepo schoolNurseRepo,
                          ManagerRepo adminRepository, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.nurseRepo = nurseRepo;
        this.schoolNurseRepo = schoolNurseRepo;
        this.adminRepository = adminRepository;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Tìm người dùng theo username và role
            UserDetails userDetails = userDetailsService.loadUserByUsernameAndRole(
                    loginRequest.getEmailName(), loginRequest.getRole()
            );
            if (userDetails == null) {
                return ResponseEntity.status(401).body("Tài khoản không tồn tại hoặc vai trò không đúng!");
            }
            // Kiểm tra mật khẩu trực tiếp thay vì dùng AuthenticationManager
            boolean passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword());
            System.out.println("Kết quả kiểm tra mật khẩu: " + passwordMatches);
            
            if (!passwordMatches) {
                return ResponseEntity.status(401).body("Mật khẩu không đúng!");
            }
            
            // Tạo Authentication object thủ công
            UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetailsImpl, null, userDetailsImpl.getAuthorities()
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            List<String> roles = userDetailsImpl.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());


            return ResponseEntity.ok(new JwtResponse(

                    jwt, userDetailsImpl.getId(), userDetailsImpl.getUsername(),
                    userDetailsImpl.getEmail(), roles
            ));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(401).body("Tài khoản không tồn tại hoặc vai trò không đúng!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Vai trò không hợp lệ!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignUpRequest signUpRequest) {
//        if (adminRepository.(signUpRequest.getUsername())) {
//            return ResponseEntity.badRequest().body("Username is already taken!");
//        }
//        if (adminRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity.badRequest().body("Email is already taken!");
//        }
        Manager admin = new Manager();
        admin.setUserName(signUpRequest.getUsername());
        admin.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        admin.setEmail(signUpRequest.getEmail());
        admin.setFullName("Nguyen Van A");
        admin.setPhone("0357899455");

        Optional<Role> role = roleRepo.findById(3);
        admin.setRole(role.get());

        admin.setIsActive(1); // Giả sử 1 là trạng thái hoạt động

        // Giả sử 3 là ID của vai trò Adminf
        adminRepository.saveManager(admin);
        return ResponseEntity.ok("Admin registered successfully");
    }

    @PostMapping("/signup/nurse")
    public ResponseEntity<?> registerNurse(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (schoolNurseRepo.existsByUserName(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }
        if (schoolNurseRepo.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already taken!");
        }
        SchoolNurse nurse = new SchoolNurse();
        nurse.setUserName(signUpRequest.getUsername());
        nurse.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        nurse.setEmail(signUpRequest.getEmail());
        nurse.setFullName(signUpRequest.getFullName());

        // nurse.setLicenseNumber(signUpRequest.getLicenseNumber());
        nurse.setRoleID(2);
        nurse.setPhone(signUpRequest.getPhone());
        nurse.setIsActive(1);
        nurse.setCertification(signUpRequest.getCertification());
        nurse.setSpecialisation(signUpRequest.getSpecialisation());
        nurseRepo.save(nurse);
        return ResponseEntity.ok("Nurse registered successfully");
    }
//
//    @PostMapping("/signup/parent")
//    public ResponseEntity<?> registerParent(@Valid @RequestBody SignUpRequest signUpRequest) {
//        if (parentRepository.existsByUserName(signUpRequest.getUsername())) {
//            return ResponseEntity.badRequest().body("Username is already taken!");
//        }
//        if (parentRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity.badRequest().body("Email is already taken!");
//        }
//        Parent parent = new Parent();
//        parent.setUsername(signUpRequest.getUsername());
//        parent.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
//        parent.setEmail(signUpRequest.getEmail());
//        parent.setChildId(signUpRequest.getChildId());
//        parentRepository.save(parent);
//        return ResponseEntity.ok("Parent registered successfully");
//    }

//    @PostMapping("/encode-password")
//    public ResponseEntity<?> encodePassword(@RequestBody String plainPassword) {
//        String encodedPassword = passwordEncoder.encode(plainPassword);
//        return ResponseEntity.ok("Encoded password: " + encodedPassword);
//    }

    @PostMapping("/manual-check")
    public ResponseEntity<?> manualCheckPassword(@RequestBody LoginRequest loginRequest) {
        try {
            // Tìm người dùng theo username và role
            UserDetails userDetails = userDetailsService.loadUserByUsernameAndRole(
                    loginRequest.getEmailName(), loginRequest.getRole()
            );
            
            // Lấy mật khẩu đã mã hóa từ DB
            String storedPassword = userDetails.getPassword();
            
            // Kiểm tra trực tiếp với PasswordEncoder
            boolean matches = passwordEncoder.matches(loginRequest.getPassword(), storedPassword);
            
            return ResponseEntity.ok("Kết quả kiểm tra mật khẩu: " + matches + 
                                    "\nMật khẩu từ form: " + loginRequest.getPassword() + 
                                    "\nMật khẩu trong DB: " + storedPassword);
            
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(401).body("Tài khoản không tồn tại hoặc vai trò không đúng!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi: " + e.getMessage());
        }
    }

//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@RequestBody LoginRequest loginRequest) {
//        try {
//            // Tìm user theo role
//            String newPlainPassword = "123456"; // Mật khẩu mới đơn giản
//            String encodedPassword = passwordEncoder.encode(newPlainPassword);
//
//            if ("NURSE".equalsIgnoreCase(loginRequest.getRole())) {
//                SchoolNurse nurse = schoolNurseRepo.(loginRequest.getUsername());
//                if (nurse == null) {
//                    return ResponseEntity.status(404).body("Không tìm thấy y tá với username: " + loginRequest.getUsername());
//                }
//                nurse.setPassword(encodedPassword);
//                nurseRepo.save(nurse);
//            }
//            // Thêm các role khác ở đây nếu cần
//
//            return ResponseEntity.ok("Đã reset mật khẩu thành: " + newPlainPassword +
//                                    "\nMật khẩu đã mã hóa: " + encodedPassword);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Lỗi khi reset mật khẩu: " + e.getMessage());
//        }
//    }

    @PostMapping("/test-bcrypt")
    public ResponseEntity<?> testBCrypt(@RequestBody String password) {
        try {
            // Tạo mã hóa mới
            String encoded1 = passwordEncoder.encode(password);
            String encoded2 = passwordEncoder.encode(password);
            
            // Kiểm tra xem 2 mã hóa có khớp không (sẽ khác vì salt ngẫu nhiên)
            boolean sameEncoding = encoded1.equals(encoded2);
            
            // Kiểm tra xem có thể matches được không
            boolean canVerify1 = passwordEncoder.matches(password, encoded1);
            boolean canVerify2 = passwordEncoder.matches(password, encoded2);
            
            return ResponseEntity.ok("Kiểm tra BCrypt:\n" +
                                    "Mã hóa 1: " + encoded1 + "\n" +
                                    "Mã hóa 2: " + encoded2 + "\n" +
                                    "Hai chuỗi mã hóa giống nhau: " + sameEncoding + "\n" +
                                    "Có thể verify với mã 1: " + canVerify1 + "\n" +
                                    "Có thể verify với mã 2: " + canVerify2);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi test BCrypt: " + e.getMessage());
        }
    }
}
