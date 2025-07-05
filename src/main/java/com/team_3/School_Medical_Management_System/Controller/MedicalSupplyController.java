package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.MedicalSupplyDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.MedicalSupplyServiceInterFace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-supplies")
public class MedicalSupplyController {
    private  MedicalSupplyServiceInterFace medicalSupplyService;

    @Autowired
    public MedicalSupplyController(MedicalSupplyServiceInterFace medicalSupplyService) {
        this.medicalSupplyService = medicalSupplyService;
    }

    @GetMapping
    public ResponseEntity<List<MedicalSupplyDTO>> getAll() {
        List<MedicalSupplyDTO> list = medicalSupplyService.getAllMedicalSupply();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<MedicalSupplyDTO> add(@RequestBody MedicalSupplyDTO dto) {
        MedicalSupplyDTO result = medicalSupplyService.addMedicalSupply(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalSupplyDTO> update(
            @PathVariable Integer id,
            @RequestBody MedicalSupplyDTO dto
    ) {
        dto.setMedicalSupplyID(id); // Gán ID vào DTO
        MedicalSupplyDTO result = medicalSupplyService.updateMedicalSupply(dto);
        return ResponseEntity.ok(result);
    }



}
