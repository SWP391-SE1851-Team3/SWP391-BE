package com.team_3.School_Medical_Management_System.security;

import com.team_3.School_Medical_Management_System.DTO.LoginRequest;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ManagerRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ParentRepository;
import com.team_3.School_Medical_Management_System.InterfaceRepo.SchoolNurseRepository;
import com.team_3.School_Medical_Management_System.Model.Manager;
import com.team_3.School_Medical_Management_System.Model.Parent;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import com.team_3.School_Medical_Management_System.Repositories.ManagerRepo;
import com.team_3.School_Medical_Management_System.Repositories.ParentRepo;
import com.team_3.School_Medical_Management_System.Repositories.SchoolNurseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private SchoolNurseRepo nurseRepository;
    private ManagerRepo adminRepository;
    private ParentRepo parentRepository;


    @Autowired
    public UserDetailsServiceImpl(SchoolNurseRepo nurseRepository, ManagerRepo adminRepository, ParentRepo parentRepository) {
        this.nurseRepository = nurseRepository;
        this.adminRepository = adminRepository;
        this.parentRepository = parentRepository;
    }



    public UserDetails loadUserByUsernameAndRole(String emailName, String role) throws UsernameNotFoundException {
        if ("NURSE".equalsIgnoreCase(role)) {
            SchoolNurse nurse = nurseRepository.findByEmailLogin(emailName);
            if (nurse == null) {
                throw new UsernameNotFoundException("Nurse not found: " + emailName);
            }

            return new UserDetailsImpl(
                    Long.valueOf(nurse.getNurseID()), nurse.getUserName(), nurse.getEmail(),
                    nurse.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_NURSE"))
            );
        } else if ("ADMIN".equalsIgnoreCase(role)) {
            Manager admin = adminRepository.findByEmailName(emailName);
            if (admin == null) {
                throw new UsernameNotFoundException("Admin not found: " + emailName);
            }

            return new UserDetailsImpl(
                    Long.valueOf(admin.getManagerID()), admin.getUserName(), admin.getEmail(),
                    admin.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        } else if ("PARENT".equalsIgnoreCase(role)) {
            Parent parent = parentRepository.getParentByEmailName(emailName);
            if (parent == null) {
                throw new UsernameNotFoundException("Parent not found: " + emailName);
            }
            return new UserDetailsImpl(
                   Long.valueOf(parent.getParentID()), parent.getUserName(), parent.getEmail(),
                    parent.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_PARENT"))
            );
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Có thể giữ logic tuần tự nếu cần hỗ trợ đăng nhập không gửi role
        throw new UsernameNotFoundException("Hahhha");
    }
}
