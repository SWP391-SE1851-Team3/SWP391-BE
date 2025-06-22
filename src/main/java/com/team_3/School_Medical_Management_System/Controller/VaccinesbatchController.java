package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.StatusUpdateDTO;
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
        if (p != null) {
            return new ResponseEntity<>(p, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
    }

    @PutMapping("/editByVaccinebatch/{id}")
    public ResponseEntity<Vaccine_Batches_EditDTO> updateVaccine(
            @PathVariable("id") int id,
            @RequestBody Vaccine_Batches_EditDTO vaccinesDTO) {

        vaccinesDTO.setBatchID(id); // set thủ công trước khi gọi service

        if (vaccinesServiceInterFace.getVaccineByID(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var updated = vaccinesServiceInterFace.UpdateVaccinebatch(vaccinesDTO);
        return updated != null
                ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/admin/consent-forms/{id}/status")
    public ResponseEntity<?> updateConsentFormStatus(
            @PathVariable("id") int id,
            @RequestBody StatusUpdateDTO statusDTO) {

        boolean updated = vaccinesServiceInterFace.updateConsentFormStatus(id, statusDTO.getStatus());
        if (updated) {
            return ResponseEntity.ok("Status updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consent form not found");
        }
    }
}
