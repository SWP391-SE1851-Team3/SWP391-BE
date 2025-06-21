package com.team_3.School_Medical_Management_System.Controller;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_BatchesDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_Batches_EditDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Vaccine_BatchesServiceInterFace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vaccinebatches")
@CrossOrigin(origins = "http://localhost:5173")
public class VaccinesbatchController {
    private Vaccine_BatchesServiceInterFace vaccinesServiceInterFace;
    @Autowired
    public VaccinesbatchController(Vaccine_BatchesServiceInterFace vaccinesServiceInterFace) {
        this.vaccinesServiceInterFace = vaccinesServiceInterFace;
    }
    @GetMapping
    public List<Vaccine_BatchesDTO> getAllVaccines() {
        return vaccinesServiceInterFace.GetAllVaccinesbatch();
    }

    @GetMapping("/{VaccineName}")
    public Vaccine_BatchesDTO getVaccine(@PathVariable String VaccineName) {
        return vaccinesServiceInterFace.GetVaccineByVaccineName(VaccineName);
    }

    @PostMapping
    public ResponseEntity<Vaccine_BatchesDTO> addVaccine(@RequestBody Vaccine_BatchesDTO vaccines) {
        var p = vaccinesServiceInterFace.AddVaccinebatch(vaccines);
        if(p != null) {
            return new ResponseEntity<>(p, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{editbyId}")
    public ResponseEntity<Vaccine_Batches_EditDTO> updateVaccine(@RequestBody Vaccine_Batches_EditDTO vaccinesDTO) {
        var updatedVaccine = vaccinesServiceInterFace.UpdateVaccinebatch(vaccinesDTO);
        if (updatedVaccine != null) {
            return new ResponseEntity<>(updatedVaccine, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
