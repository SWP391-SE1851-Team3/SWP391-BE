package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.VaccineTypeShortDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_TypesDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_Types_Edit_DTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.Vaccine_TypesServiceInterFace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaccine_types")
public class Vaccine_TypesController {
    private Vaccine_TypesServiceInterFace vaccine_typesService;

    @Autowired
    public Vaccine_TypesController(Vaccine_TypesServiceInterFace vaccine_typesService) {
        this.vaccine_typesService = vaccine_typesService;
    }

    @GetMapping
    public List<Vaccine_TypesDTO> getAllVaccineTypes() {
        return vaccine_typesService.getVaccine_Types();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVaccineTypeById(@PathVariable int id) {
        try {
            VaccineTypeShortDTO dto = vaccine_typesService.getVaccineType(id);
            return dto != null
                    ? ResponseEntity.ok(dto)
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaccine type not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> addVaccineType(@RequestBody Vaccine_TypesDTO dto) {
        try {
            Vaccine_TypesDTO addVaccineTypes = vaccine_typesService.addVaccine_Types(dto);
            return addVaccineTypes != null ? ResponseEntity.ok(dto)
                    : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<?> deleteVaccineType(@PathVariable int id) {
        try {
            Vaccine_TypesDTO deleteVaccineType = vaccine_typesService.deleteVaccine_Types(id);
            return deleteVaccineType != null ? ResponseEntity.ok(deleteVaccineType)
                    : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + deleteVaccineType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/edit_vaccineType/{id}")
    public ResponseEntity<?> editVaccineType(
            @PathVariable("id") int id,
            @RequestBody Vaccine_Types_Edit_DTO dto) {
        if (id <= 0) {
            return ResponseEntity.badRequest().body("Invalid Vaccine Type ID: " + id);
        }
        dto.setVaccineTypeID(id);
        try {
            Vaccine_TypesDTO existing = vaccine_typesService.getVaccine_TypeByID(id);
            if (existing == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Vaccine type not found with ID: " + id);
            }
            Vaccine_Types_Edit_DTO updated = vaccine_typesService.updateVaccine_Types(dto);

            if (updated != null) {
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to update vaccine type with ID: " + id);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Exception occurred: " + e.getMessage());
        }
    }

    @GetMapping("/getByVacinesName")
    public ResponseEntity<?> getVaccineTypeByVacinesName() {
        try {
            List<VaccineTypeShortDTO> getName = vaccine_typesService.getVaccine_TypeByName();
            return getName != null ? ResponseEntity.ok(getName) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + getName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
