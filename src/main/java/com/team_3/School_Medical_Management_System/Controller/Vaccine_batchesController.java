package com.team_3.School_Medical_Management_System.Controller;
import com.team_3.School_Medical_Management_System.DTO.Vaccine_batchesDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Vaccine_batchesSerivceInterFace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaccine_batches")
@CrossOrigin(origins = "http://localhost:5173")
public class Vaccine_batchesController {

    private Vaccine_batchesSerivceInterFace vaccine_batchesSerivce;

    @Autowired
    public Vaccine_batchesController(Vaccine_batchesSerivceInterFace vaccine_batchesSerivce) {
        this.vaccine_batchesSerivce = vaccine_batchesSerivce;
    }

    @GetMapping
    public List<Vaccine_batchesDTO> getAllVaccineBatches() {
        return vaccine_batchesSerivce.getAll();
    }

    @GetMapping("/{id}")
    public Vaccine_batchesDTO getVaccineBatchById(@PathVariable int id) {
        return vaccine_batchesSerivce.getById(id);
    }

    @PostMapping
    public ResponseEntity<Void> addVaccineBatch(@RequestBody Vaccine_batchesDTO vaccine_batches) {
        vaccine_batchesSerivce.add(vaccine_batches);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{edit_batch}")
    public ResponseEntity<Void> updateVaccineBatch(Vaccine_batchesDTO vaccine_batch) {
        vaccine_batchesSerivce.update(vaccine_batch);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/totalvaccine_batches")
    public ResponseEntity<Long> totalVaccineBatches() {
        var count = vaccine_batchesSerivce.countTotalBatch();
        return ResponseEntity.ok(count);
    }

}
