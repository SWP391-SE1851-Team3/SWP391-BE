package com.team_3.School_Medical_Management_System.Service;
import com.team_3.School_Medical_Management_System.DTO.Vaccination_recordsDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Vaccination_recordsServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Vaccination_recordsInterFace;
import com.team_3.School_Medical_Management_System.Model.Vaccination_records;
import com.team_3.School_Medical_Management_System.Model.Vaccination_schedule;
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
    private StudentRepo studentRepo;

    @Autowired
    private VaccinesRepo vaccinesRepo;

    @Autowired
    private Vaccine_batchesRepo vaccineBatchesRepo;

    @Autowired
    private Vaccination_scheduleRepo vaccination_scheduleRepo;

    @Autowired
    private SchoolNurseRepo schoolNurseRepo;


    @Autowired
    public Vaccination_recordsService(Vaccination_recordsInterFace vaccination_recordsInterFace) {
        this.vaccination_recordsInterFace = vaccination_recordsInterFace;
    }

    @Override
    public List<Vaccination_recordsDTO> getVaccination_records() {
        var list = vaccination_recordsInterFace.getVaccination_records();
        return list.stream().map(TransferModelsDTO :: MappingVaccinationRecordsDTO).collect(Collectors.toList());
    }

    @Override
    public Vaccination_recordsDTO addVaccination_records(Vaccination_recordsDTO vaccinationRecordsDTO) {
        var student = studentRepo.GetStudentByFullName(vaccinationRecordsDTO.getStudent_Name());
        if(student == null){
            throw new RuntimeException("student doesn't exist");
        }

        var vaccinesName = vaccinesRepo.GetVaccineByVaccineName(vaccinationRecordsDTO.getVaccine_Name());
        if(vaccinesName == null){
            throw new RuntimeException("vaccine_Name doesn't exist");
        }

        var nurseName = schoolNurseRepo.getSchoolNursesByName(vaccinationRecordsDTO.getNurse_Name());
        if(nurseName == null){
            throw new RuntimeException("nurse_Name doesn't exist");
        }

        var bacth_Id = vaccineBatchesRepo.getById(vaccinationRecordsDTO.getBatch_id());
        if(bacth_Id == null){
            throw new RuntimeException("batch_id doesn't exist");
        }

        var scheduleId = vaccination_scheduleRepo.vaccination_scheduleById(vaccinationRecordsDTO.getSchedule_id());
        if(scheduleId == null){
            throw new RuntimeException("schedule_id doesn't exist");
        }

        // Không kiểm tra vaccinationRecordID nữa
        var vaccinationRecord = new Vaccination_records();
        vaccinationRecord.setStudent(student);
        vaccinationRecord.setVaccines(vaccinesName);
        vaccinationRecord.setNurse(nurseName);
        vaccinationRecord.setBatch(bacth_Id);
        vaccinationRecord.setSchedule(scheduleId);
        vaccinationRecord.setNotes(vaccinationRecordsDTO.getNotes());
        vaccinationRecord.setSymptoms(vaccinationRecordsDTO.getSymptoms());
        vaccinationRecord.setObservation_notes(vaccinationRecordsDTO.getObservation_notes());
        vaccinationRecord.setSeverity(vaccinationRecordsDTO.getSeverity());
        vaccination_recordsInterFace.addVaccination_records(vaccinationRecord);

        return TransferModelsDTO.MappingVaccinationRecordsDTO(vaccinationRecord);
    }

    @Override
    public void deleteVaccination_records(int id) {
      vaccination_recordsInterFace.deleteVaccination_records(id);
    }

    @Override
    public Vaccination_recordsDTO getVaccination_records_by_id(int id) {
        var vaccination_records_by_id  = vaccination_recordsInterFace.getVaccination_records_by_id(id);
        if(vaccination_records_by_id == null)
        {
            throw  new RuntimeException("Vaccination_records_by_id is null");
        }else {
            return TransferModelsDTO.MappingVaccinationRecordsDTO(vaccination_records_by_id);
        }
    }

    @Override
    public Vaccination_recordsDTO updateVaccination_records(Vaccination_recordsDTO vaccinationRecordsDTO) {
        var student = studentRepo.GetStudentByFullName(vaccinationRecordsDTO.getStudent_Name());
        if(student == null){
            throw  new RuntimeException("student doesn't exist");
        }
        var vaccinesName = vaccinesRepo.GetVaccineByVaccineName(vaccinationRecordsDTO.getVaccine_Name());
        if(vaccinesName == null){
            throw  new RuntimeException("vaccine_Name doesn't exist");
        }

        var nurseName = schoolNurseRepo.getSchoolNursesByName(vaccinationRecordsDTO.getNurse_Name());
        if(nurseName == null){
            throw new RuntimeException("nurse_Name doesn't exist");
        }
        var bacth_Id = vaccineBatchesRepo.getById(vaccinationRecordsDTO.getBatch_id());
        if(bacth_Id == null){
            throw  new RuntimeException("batch_id doesn't exist");
        }

        var scheduleId = vaccination_scheduleRepo.vaccination_scheduleById(vaccinationRecordsDTO.getSchedule_id());
        if(scheduleId == null){
            throw new RuntimeException("schedule_id doesn't exist");
        }

        var vaccinationRecordOptional = vaccination_recordsInterFace.getVaccination_records_by_id(vaccinationRecordsDTO.getVaccinationRecordID());
        if (vaccinationRecordOptional == null) {
            throw new RuntimeException("Vaccination record not found");
        }
        var vaccinationRecord = vaccinationRecordOptional;
        // Thực hiện cập nhật các trường
        vaccinationRecord.setStudent(student);
        vaccinationRecord.setVaccines(vaccinesName);
        vaccinationRecord.setNurse(nurseName);
        vaccinationRecord.setBatch(bacth_Id);
        vaccinationRecord.setSchedule(scheduleId);
        vaccinationRecord.setNotes(vaccinationRecordsDTO.getNotes());
        vaccinationRecord.setSymptoms(vaccinationRecordsDTO.getSymptoms());
        vaccinationRecord.setObservation_notes(vaccinationRecordsDTO.getObservation_notes());
        vaccinationRecord.setSeverity(vaccinationRecordsDTO.getSeverity());
        vaccination_recordsInterFace.updateVaccination_records(vaccinationRecord);
        return TransferModelsDTO.MappingVaccinationRecordsDTO(vaccinationRecord);
    }


    @Override
    public List<Vaccination_recordsDTO> getVaccination_recordsByStudentId(int studentId) {
       var vaccination_recordsByStudentId = vaccination_recordsInterFace.getVaccination_recordsByStudentId(studentId);
       if(vaccination_recordsByStudentId == null || vaccination_recordsByStudentId.isEmpty()){
           throw  new RuntimeException("vaccination_records_student is null");
       }else {
           return vaccination_recordsByStudentId.stream().map(TransferModelsDTO::MappingVaccinationRecordsDTO).collect(Collectors.toList());
       }
    }

    @Override
    public List<Vaccination_recordsDTO> getVaccination_recordsByStudentId(int studentId) {
       var vaccination_recordsByStudentId = vaccination_recordsInterFace.getVaccination_recordsByStudentId(studentId);
       if(vaccination_recordsByStudentId == null || vaccination_recordsByStudentId.isEmpty()){
           throw  new RuntimeException("vaccination_records_student is null");
       }else {
           return vaccination_recordsByStudentId.stream().map(TransferModelsDTO::MappingVaccinationRecordsDTO).collect(Collectors.toList());
       }
    }
}
