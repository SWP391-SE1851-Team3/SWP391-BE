package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.ChangePasswordRequest;
import com.team_3.School_Medical_Management_System.DTO.LoginResponse;
import com.team_3.School_Medical_Management_System.DTO.ParentDTO;
import com.team_3.School_Medical_Management_System.Model.Parent;
import com.team_3.School_Medical_Management_System.DTO.ParentLoginResponseDTO;
import com.team_3.School_Medical_Management_System.Service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/parents")
@CrossOrigin(origins = "*")
public class ParentController {

    private ParentService parentService;

    @Autowired
    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping
    public List<Parent> getParents() {
        return parentService.getParents();
    }

    @GetMapping("/{fullName}")
    public ResponseEntity<Parent> getParent(@PathVariable String fullName) {
        Parent p = parentService.getParentByName(fullName);
        return ResponseEntity.ok(p);
    }


    @PostMapping
    public ResponseEntity<Void> addParent(@RequestBody Parent Parent) {
        var p = parentService.GetParentById(Parent.getParentID());
        if (p == null) {
            parentService.AddNewParent(Parent);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Parent> updateParent(@PathVariable("id") int id, @RequestBody ParentDTO parentDTO) {
        Parent p = parentService.GetParentById(id);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // Bạn có thể validate parent.getFullName() ở đây hoặc trong service
        p.setPhone(parentDTO.getPhone());
        p.setEmail(parentDTO.getEmail());
        p.setOccupation(parentDTO.getOccupation());
        Parent updated = parentService.UpdateParent(p);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ParentLoginResponseDTO parentLoginResponseDTO) {
        var p = parentService.LoginByAccount(parentLoginResponseDTO.getEmail(), parentLoginResponseDTO.getPassword());
        if (p != null) {
            return ResponseEntity.ok(new LoginResponse(p.getEmail(), 3));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password incorrect");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable int id) {
        var p = parentService.GetParentById(id);
        if (p != null) {
            parentService.DeleteParent(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        boolean succes = parentService.changePassword(changePasswordRequest.getEmail(), changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
        if (succes) {
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect");
        }
    }


}


















