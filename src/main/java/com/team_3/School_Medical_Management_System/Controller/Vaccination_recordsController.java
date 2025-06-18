package com.team_3.School_Medical_Management_System.Controller;
import com.team_3.School_Medical_Management_System.DTO.Vaccination_recordsDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Vaccination_recordsServiceInterFace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/vaccination_records")
@CrossOrigin(origins = "http://localhost:5173")
public class Vaccination_recordsController {

    private Vaccination_recordsServiceInterFace vaccination_recordsServiceInterFace;
    @Autowired
    public Vaccination_recordsController(Vaccination_recordsServiceInterFace vaccination_recordsServiceInterFace) {
        this.vaccination_recordsServiceInterFace = vaccination_recordsServiceInterFace;
    }

    @GetMapping
    public List<Vaccination_recordsDTO> getVaccination_records() {
        return vaccination_recordsServiceInterFace.getVaccination_records();
    }


    @PostMapping
    public ResponseEntity<Vaccination_recordsDTO> addVaccination_records(@RequestBody Vaccination_recordsDTO vaccination_records) {
        var addVaccination_Record = vaccination_recordsServiceInterFace.addVaccination_records(vaccination_records);
        if(addVaccination_Record != null) {
            return new ResponseEntity<>(addVaccination_Record, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/vaccination-records/{id}")
    public ResponseEntity<Vaccination_recordsDTO> updateVaccinationRecord(
            @PathVariable int id,
            @RequestBody Vaccination_recordsDTO dto) {
        vaccination_records.setVaccinationRecordID(id); // Gán id từ URL vào DTO
        Vaccination_recordsDTO updatedRecord = vaccination_recordsServiceInterFace.updateVaccination_records(vaccination_records);
        return ok(updatedRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaccination_recordsDTO> getVaccination_records(@PathVariable int id) {
        var vaccinationRecordById = vaccination_recordsServiceInterFace.getVaccination_records_by_id(id);
        if(vaccinationRecordById != null) {
            return new ResponseEntity<>(vaccinationRecordById, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteVaccination_records(@PathVariable int id) {
        vaccination_recordsServiceInterFace.deleteVaccination_records(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/byStudentId/{studentId}")
    public ResponseEntity<?>  findByStudentId(@PathVariable int studentId) {
        List<Vaccination_recordsDTO> studentRecordById = vaccination_recordsServiceInterFace.getVaccination_recordsByStudentId(studentId);
        if(studentRecordById == null || studentRecordById.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy bản ghi tiêm chủng cho sinh viên có id: " + studentId);
        }else {
            return ResponseEntity.ok(studentRecordById);
        }
    }




}
