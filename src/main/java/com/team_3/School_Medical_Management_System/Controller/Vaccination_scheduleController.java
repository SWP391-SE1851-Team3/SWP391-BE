package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.Vaccination_scheduleDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Vaccination_scheduleServiceInterFace;
import com.team_3.School_Medical_Management_System.Model.Vaccination_schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaccination_scheduleControllers")
public class Vaccination_scheduleController {
    private Vaccination_scheduleServiceInterFace vaccination_scheduleServiceInterFace;

    @Autowired
    public Vaccination_scheduleController(Vaccination_scheduleServiceInterFace vaccination_scheduleServiceInterFace) {
        this.vaccination_scheduleServiceInterFace = vaccination_scheduleServiceInterFace;
    }

    @GetMapping
    public List<Vaccination_scheduleDTO> getAllVaccination_schedules() {
        return vaccination_scheduleServiceInterFace.vaccination_schedules();
    }

    @PostMapping
    public Vaccination_scheduleDTO addVaccination_schedule(@RequestBody Vaccination_scheduleDTO vaccination_scheduleDTO) {
        return vaccination_scheduleServiceInterFace.addVaccination_schedule(vaccination_scheduleDTO);
    }

    @GetMapping("/{id}")
    public Vaccination_scheduleDTO getVaccination_schedule(@PathVariable int id) {
        return vaccination_scheduleServiceInterFace.vaccination_scheduleById(id);
    }


    @PutMapping("/{id}")
    public Vaccination_scheduleDTO updateVaccination_schedule(@RequestBody Vaccination_scheduleDTO vaccination_scheduleDTO) {
        vaccination_scheduleServiceInterFace.updateVaccination_schedule(vaccination_scheduleDTO);
        return vaccination_scheduleDTO;
    }

}
