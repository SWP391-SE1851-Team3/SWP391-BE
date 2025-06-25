package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.*;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Vaccination_recordsServiceInterFace;
import com.team_3.School_Medical_Management_System.Model.Vaccination_records;
import com.team_3.School_Medical_Management_System.configuration.EmailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaccination_records")
public class Vaccination_recordsController {
    @Autowired
    private EmailConfig emailConfig;

    private Vaccination_recordsServiceInterFace vaccination_recordsServiceInterFace;

    @Autowired
    public Vaccination_recordsController(Vaccination_recordsServiceInterFace vaccination_recordsServiceInterFace) {
        this.vaccination_recordsServiceInterFace = vaccination_recordsServiceInterFace;
    }

    @GetMapping
    public List<Vaccination_recordsDTO> getVaccination_records() {
        return vaccination_recordsServiceInterFace.getVaccination_records();
    }

    @GetMapping("/{id}")
    public Vaccination_recordsDTO getVaccination_records(@PathVariable int id) {
        return vaccination_recordsServiceInterFace.getVaccination_records_by_id(id);
    }

    @PostMapping
    public ResponseEntity<Vaccination_recordsDTO> addVaccination_records(@RequestBody Vaccination_recordsDTO records) {
        var p = vaccination_recordsServiceInterFace.addVaccination_records(records);
        return ResponseEntity.ok().body(p);
    }

    @GetMapping("/vaccination_records/by-student/{studentId}")
    public List<Vaccination_recordsDTO> getVaccination_records_by_studendId(@PathVariable int studentId) {
        return vaccination_recordsServiceInterFace.getVaccination_recordsByStudentId(studentId);
    }

    @PutMapping("/editVaccineRecord/{id}")
    public ResponseEntity<Vaccination_records_edit_DTO> editVaccination_records(
            @PathVariable("id") int id,
            @RequestBody Vaccination_records_edit_DTO vaccinationRecordsEditDto) {
        vaccinationRecordsEditDto.setVaccinationRecordID(id);
        var getID = vaccination_recordsServiceInterFace.getVaccination_records_by_id(id);
        if (getID == null) {
            return ResponseEntity.notFound().build();
        } else {
            var p = vaccination_recordsServiceInterFace.updateVaccination_records(vaccinationRecordsEditDto);
            if (p == null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok().body(p);
            }
        }
    }

    @PostMapping("/vaccination-records/send-email")
    public ResponseEntity<Vaccination_records_SentParent_DTO> sendVaccinationEmail(
            @RequestBody Vaccination_records_SentParent_DTO records) {
        var result = vaccination_recordsServiceInterFace.createEmail(records);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/vaccination-records/resend/{recordId}")
    public ResponseEntity<?> resend(@PathVariable Integer recordId, @RequestBody Vaccination_records_SentParent_Edit_DTO dto) {
        var result = vaccination_recordsServiceInterFace.updateAndResendEmail(recordId, dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/StudentFollowedbyNurse")
    public List<StudentVaccinationDTO> getStudentFollowedbyNurse() {
        return vaccination_recordsServiceInterFace.getStudentFollowedbyNurse();
    }




}
