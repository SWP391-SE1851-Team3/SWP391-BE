package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.Consent_formsDTO;
import com.team_3.School_Medical_Management_System.DTO.Consent_formsRequestDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ConsentFormsRepository;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Consent_formsServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Consent_formsInterFace;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.*;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class Consent_formsSerivce implements Consent_formsServiceInterFace {
    private Consent_formsInterFace consent_formsRepo;
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private VaccinesRepo vaccinesRepo;

    @Autowired
    private Vaccination_scheduleRepo vaccination_scheduleRepo;

    @Autowired
    private ParentRepo parentRepo;

    @Autowired
    private ConsentFormsRepository consent_forms_repo;


    @Autowired
    public Consent_formsSerivce(Consent_formsInterFace consent_formsRepo) {
        this.consent_formsRepo = consent_formsRepo;
    }

    @Override
    public List<Consent_formsDTO> getConsent_forms() {
        var consent_forms = consent_formsRepo.getConsent_forms();
        return consent_forms.stream().map(TransferModelsDTO::MappingConsent).collect(Collectors.toList());
    }

    @Override
    public Consent_formsDTO addConsent_forms(Consent_formsRequestDTO dto) {
        Student student = studentRepo.getStudent(dto.getStudent_ID());
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        Vaccines vaccine = vaccinesRepo.GetVaccineByVaccineId(dto.getVaccine_ID());
        if (vaccine == null) {
            throw new RuntimeException("Vaccine not found");
        }

        Parent parent = parentRepo.GetParentById(dto.getPatient_ID());
        if (parent == null) {
            throw new RuntimeException("Parent not found");
        }

        Vaccination_schedule schedule = vaccination_scheduleRepo.vaccination_scheduleById(dto.getScheduleId());
        if (schedule == null) {
            throw new RuntimeException("Schedule not found");
        }
        Consent_forms entity = new Consent_forms();
        entity.setStudent(student);
        entity.setParent(parent);
        entity.setVaccine(vaccine);
        entity.setSchedule(schedule);
        entity.setIsAgree(dto.getIsAgree());
        entity.setHasAllergy(dto.getHasAllergy());
        entity.setReason(dto.getReason());
        entity.setConsent_date(new Date());
        consent_formsRepo.addConsent_forms(entity);
        return null;
    }


    @Override
    public List<Consent_formsDTO> getConsent_formsByParentName(String fullName) {
        var consent_formsName = consent_formsRepo.getConsent_formsByParentName(fullName);
        return consent_formsName.stream().map(TransferModelsDTO::MappingConsent).collect(Collectors.toList());
    }


    @Override
    public Consent_formsDTO getConsent_formsByInfor(int studentId, int scheduleId) {
        // 1. Lấy student và parent
        Student student = studentRepo.getStudent(studentId);
        if (student == null) throw new RuntimeException("Student not found");

        Parent parent = student.getParent();
        if (parent == null) throw new RuntimeException("Parent not found");

        // 2. Lấy schedule và vaccine
        Vaccination_schedule schedule = vaccination_scheduleRepo.vaccination_scheduleById(scheduleId);
        if (schedule == null) throw new RuntimeException("Schedule not found");

        Vaccines vaccines = schedule.getVaccine();
        if (vaccines == null) throw new RuntimeException("Vaccine not found");

        // 3. Tạo DTO cơ bản
        Consent_formsDTO dto = new Consent_formsDTO();
        dto.setFullNameOfStudent(student.getFullName());
        dto.setFullnameOfParent(parent.getFullName());
        dto.setClassName(student.getClassName());
        dto.setScheduled_date(schedule.getScheduled_date());
        dto.setNotes(schedule.getNotes());
        dto.setName(vaccines.getName());

        // 4. Lấy consent gần nhất nếu có
        consent_forms_repo
                .findLatestByStudentAndSchedule(studentId, scheduleId)
                .ifPresent(cf -> {
                    dto.setHasAllergy(cf.getHasAllergy());
                    dto.setReason(cf.getReason());
                    dto.setIsAgree(cf.getIsAgree());
                });

        return dto;
    }

    @Override
    public List<Consent_formsDTO> getConsent_formsIsAgree() {
        var consent_forms = consent_formsRepo.getConsent_formsIsAgree();
        return consent_forms.stream().map(TransferModelsDTO::MappingConsent).collect(Collectors.toList());
    }


}
