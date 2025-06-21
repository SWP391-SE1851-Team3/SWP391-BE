package com.team_3.School_Medical_Management_System.Service;
import com.team_3.School_Medical_Management_System.DTO.Vaccination_recordsDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccination_records_edit_DTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Vaccination_recordsServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Vaccination_recordsInterFace;
import com.team_3.School_Medical_Management_System.Model.Vaccination_records;
import com.team_3.School_Medical_Management_System.Repositories.*;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class Vaccination_recordsService implements Vaccination_recordsServiceInterFace {
    private Vaccination_recordsInterFace vaccination_recordsInterFace;

    @Autowired
    public Vaccination_recordsService(Vaccination_recordsInterFace vaccination_recordsInterFace) {
        this.vaccination_recordsInterFace = vaccination_recordsInterFace;
    }

    @Override
    public List<Vaccination_recordsDTO> getVaccination_records() {
        var list = vaccination_recordsInterFace.getVaccination_records();
        return list.stream().map(TransferModelsDTO::MappingVaccinationRecords).collect(Collectors.toList());
    }

    @Override
    public Vaccination_recordsDTO addVaccination_records(Vaccination_recordsDTO vaccinationRecordsDTO) {
        Vaccination_records p  = vaccination_recordsInterFace.addVaccination_records(TransferModelsDTO.MappingVaccinationRecordsDTO(vaccinationRecordsDTO));
        return TransferModelsDTO.MappingVaccinationRecords(p);
    }

    @Override
    public void deleteVaccination_records(int id) {
        vaccination_recordsInterFace.deleteVaccination_records(id);
    }

    @Override
    public Vaccination_recordsDTO getVaccination_records_by_id(int id) {
        var vaccination_records_by_id = vaccination_recordsInterFace.getVaccination_records_by_id(id);
        if (vaccination_records_by_id == null) {
            throw new RuntimeException("Vaccination_records_by_id is null");
        } else {
            return TransferModelsDTO.MappingVaccinationRecords(vaccination_records_by_id);
        }
    }

    @Override
    public Vaccination_records_edit_DTO updateVaccination_records(Vaccination_records_edit_DTO vaccination_records_edit_DTO) {
        var updateReocord = vaccination_recordsInterFace.updateVaccination_records(TransferModelsDTO.MappingVaccinationRecordsEditDTO(vaccination_records_edit_DTO));
        return TransferModelsDTO.MappingVaccinationRecordsEdit(updateReocord);
    }
    @Override
    public List<Vaccination_recordsDTO> getVaccination_recordsByStudentId(int studentId) {
        var vaccination_recordsByStudentId = vaccination_recordsInterFace.getVaccination_recordsByStudentId(studentId);
        if (vaccination_recordsByStudentId == null || vaccination_recordsByStudentId.isEmpty()) {
            throw new RuntimeException("vaccination_records_student is null");
        } else {
            return vaccination_recordsByStudentId.stream().map(TransferModelsDTO::MappingVaccinationRecords).collect(Collectors.toList());
        }
    }


}