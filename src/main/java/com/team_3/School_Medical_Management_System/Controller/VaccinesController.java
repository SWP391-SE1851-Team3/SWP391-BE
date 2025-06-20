package com.team_3.School_Medical_Management_System.Controller;
import com.team_3.School_Medical_Management_System.DTO.VaccinesDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.VaccinesServiceInterFace;
import com.team_3.School_Medical_Management_System.Model.Vaccines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vaccines")
@CrossOrigin(origins = "http://localhost:5173")
public class VaccinesController {
    private VaccinesServiceInterFace vaccinesServiceInterFace;
    @Autowired
    public VaccinesController(VaccinesServiceInterFace vaccinesServiceInterFace) {
        this.vaccinesServiceInterFace = vaccinesServiceInterFace;
    }
    @GetMapping
    public List<VaccinesDTO> getAllVaccines() {
        return vaccinesServiceInterFace.GetAllVaccines();
    }

    @GetMapping("/{VaccineName}")
    public VaccinesDTO getVaccine(@PathVariable String VaccineName) {
        return vaccinesServiceInterFace.GetVaccineByVaccineName(VaccineName);
    }

    @PostMapping
    public ResponseEntity<VaccinesDTO> addVaccine(@RequestBody VaccinesDTO vaccines) {
        var p = vaccinesServiceInterFace.AddVaccine(vaccines);
        if(p != null) {
            return new ResponseEntity<>(p, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{editbyId}")
    public ResponseEntity<VaccinesDTO> updateVaccine(@RequestBody VaccinesDTO vaccinesDTO) {
        var updatedVaccine = vaccinesServiceInterFace.UpdateVaccine(vaccinesDTO);
        if (updatedVaccine != null) {
            return new ResponseEntity<>(updatedVaccine, HttpStatus.OK); // Nếu cập nhật thành công
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Nếu không tìm thấy vaccine để cập nhật
        }
    }

}
