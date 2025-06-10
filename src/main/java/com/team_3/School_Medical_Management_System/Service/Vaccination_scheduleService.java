package com.team_3.School_Medical_Management_System.Service;
import com.team_3.School_Medical_Management_System.DTO.Vaccination_scheduleDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Vaccination_scheduleServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Vaccination_scheduleInterFace;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import com.team_3.School_Medical_Management_System.Model.Vaccination_schedule;
import com.team_3.School_Medical_Management_System.Model.Vaccines;
import com.team_3.School_Medical_Management_System.Repositories.SchoolNurseRepo;
import com.team_3.School_Medical_Management_System.Repositories.VaccinesRepo;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

public class Vaccination_scheduleService implements Vaccination_scheduleServiceInterFace {
    private Vaccination_scheduleInterFace vaccination_schedule;

    @Autowired
    private VaccinesRepo vaccinesRepo;

    @Autowired
    private SchoolNurseRepo schoolNurseRepo;

    @Autowired
    public Vaccination_scheduleService(Vaccination_scheduleInterFace vaccination_schedule) {
        this.vaccination_schedule = vaccination_schedule;
    }

    @Override
    public Vaccination_scheduleDTO vaccination_scheduleById(int id) {
        Vaccination_schedule vaccine = vaccination_schedule.vaccination_scheduleById(id);
        if(vaccine == null) {
            throw new RuntimeException("Vaccine not found");
        }
        // Chuyển từ Entity sang DTO
        Vaccination_scheduleDTO vaccinationScheduleDTO = TransferModelsDTO.MappingVaccinationSchedule(vaccine);

        // Trả về DTO
        return vaccinationScheduleDTO;

    }

    @Override
    public List<Vaccination_scheduleDTO> vaccination_schedules() {
        var vaccinationList = vaccination_schedule.vaccination_schedules();
        return vaccinationList.stream().map(TransferModelsDTO::MappingVaccinationSchedule).collect(Collectors.toList());
    }

    @Override
    public Vaccination_scheduleDTO addVaccination_schedule(Vaccination_scheduleDTO vaccination_scheduleDTO) {
        Vaccination_schedule entity = new Vaccination_schedule();
        Vaccines vaccine = vaccinesRepo.GetVaccineByVaccineName(vaccination_scheduleDTO.getName());
        if(vaccine == null) {
            throw new RuntimeException("Vaccine not found");
        }
        SchoolNurse schoolNurse = schoolNurseRepo.getSchoolNursesByName(vaccination_scheduleDTO.getFullName());
        if(schoolNurse == null) {
            throw new RuntimeException("School nurse not found");
        }
        // 2. Chuyển dữ liệu từ DTO vào Entity
        entity.setBatch_number(vaccination_scheduleDTO.getBatch_number());
        entity.setLocation(vaccination_scheduleDTO.getLocation());
        entity.setStatus(vaccination_scheduleDTO.getStatus());
        entity.setNotes(vaccination_scheduleDTO.getNotes());
        entity.setScheduled_date(vaccination_scheduleDTO.getScheduled_date());
        entity.setReceived_date(vaccination_scheduleDTO.getReceived_date());
        entity.setVaccine(vaccine);
        entity.setNurse(schoolNurse);
        vaccination_schedule.addVaccination_schedule(entity);

        // 3. Trả về Entity đã được chuyển đổi từ DTO
        return TransferModelsDTO.MappingVaccinationSchedule(entity);

    }

    @Override
    public Vaccination_scheduleDTO updateVaccination_schedule(Vaccination_scheduleDTO vaccination_scheduleDTO) {
        Vaccines vaccinev1 = vaccinesRepo.GetVaccineByVaccineName(vaccination_scheduleDTO.getName());
        if(vaccinev1 == null) {
            throw new RuntimeException("Vaccine not found");
        }
        SchoolNurse schoolNurse = schoolNurseRepo.getSchoolNursesByName(vaccination_scheduleDTO.getFullName());
        if(schoolNurse == null) {
            throw new RuntimeException("School nurse not found");
        }
        Vaccination_schedule vaccine = vaccination_schedule.vaccination_scheduleById(vaccination_scheduleDTO.getSchedule_id());
        if(vaccine == null) {
            throw new RuntimeException("Vaccine not found");
        }else {
            vaccine.setBatch_number(vaccination_scheduleDTO.getBatch_number());
            vaccine.setLocation(vaccination_scheduleDTO.getLocation());
            vaccine.setStatus(vaccination_scheduleDTO.getStatus());
            vaccine.setNotes(vaccination_scheduleDTO.getNotes());
            vaccine.setScheduled_date(vaccination_scheduleDTO.getScheduled_date());
            vaccine.setReceived_date(vaccination_scheduleDTO.getReceived_date());
            vaccine.setNurse(schoolNurse);
            vaccine.setVaccine(vaccinev1);
            vaccination_schedule.updateVaccination_schedule(vaccine);
        }
        return TransferModelsDTO.MappingVaccinationSchedule(vaccine);
    }








}
