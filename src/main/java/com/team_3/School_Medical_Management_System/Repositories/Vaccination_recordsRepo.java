package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.DTO.StudentVaccinationDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Vaccination_recordsInterFace;
import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import com.team_3.School_Medical_Management_System.Model.StudentHealthProfile;
import com.team_3.School_Medical_Management_System.Model.Vaccination_records;
import com.team_3.School_Medical_Management_System.Model.Vaccine_Batches;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.team_3.School_Medical_Management_System.DTO.StudentVaccinationDTO;


import java.util.List;

@Repository
public class Vaccination_recordsRepo implements Vaccination_recordsInterFace {
    private EntityManager entityManager;

    @Autowired
    public Vaccination_recordsRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Vaccination_records> getVaccination_records() {
        try {
            String query = "SELECT r FROM Vaccination_records r";
            return entityManager.createQuery(query, Vaccination_records.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Vaccination_records addVaccination_records(Vaccination_records vaccination_records) {
        try {
            entityManager.persist(vaccination_records);
            return vaccination_records;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteVaccination_records(int id) {
        var query = getVaccination_records_by_id(id);
        if (query != null) {
            entityManager.remove(query);
        } else {
            System.out.println("Vaccination_records not found");
        }
    }

    @Override
    public Vaccination_records getVaccination_records_by_id(int id) {
        return entityManager.find(Vaccination_records.class, id);
    }

    @Override
    public Vaccination_records updateVaccination_records(Vaccination_records vaccination_records) {
        var searchid = getVaccination_records_by_id(vaccination_records.getVaccinationRecordID());
        if (searchid != null) {
            searchid.setVaccinationRecordID(vaccination_records.getVaccinationRecordID());
            searchid.setObservation_notes(vaccination_records.getObservation_notes());
            searchid.setNotes(vaccination_records.getNotes());
            searchid.setObservation_time(vaccination_records.getObservation_time());
            searchid.setSeverity(vaccination_records.getSeverity());
            SchoolNurse schoolNurse = new SchoolNurse();
            schoolNurse.setNurseID(vaccination_records.getUpdatedByNurse().getNurseID());
            schoolNurse.setFullName(vaccination_records.getUpdatedByNurse().getFullName());
            searchid.setUpdatedByNurse(schoolNurse);
            Vaccine_Batches vaccine_Batches = new Vaccine_Batches();
            vaccine_Batches.setBatchID(vaccination_records.getVaccineBatches().getBatchID());
            searchid.setVaccineBatches(vaccine_Batches);
            entityManager.merge(vaccination_records);
        } else {
            throw new RuntimeException("Vaccination_records not found");
        }
        return vaccination_records;
    }

    @Override
    public List<Vaccination_records> getVaccination_recordsByStudentId(int studentId) {
        String sql = "SELECT s FROM Vaccination_records s WHERE s.student.StudentID = :studentId";
        return entityManager.createQuery(sql, Vaccination_records.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }

    @Override
    public List<StudentVaccinationDTO> getStudentFollowedbyNurse() {
        String jpql = "SELECT new com.team_3.School_Medical_Management_System.DTO.StudentVaccinationDTO(" +
                "s.student.StudentID, s.student.FullName, s.student.className, " +
                "s.observation_notes, s.vaccineBatches.vaccineType.Name, s.observation_time,s.status, s.VaccinationRecordID) " +
                "FROM Vaccination_records s WHERE s.status = :status";
        return entityManager.createQuery(jpql, StudentVaccinationDTO.class)
                .setParameter("status", "Cần theo dõi")
                .getResultList();
    }

    @Override
    public StudentVaccinationDTO updateStudentFollowedbyNurse(StudentVaccinationDTO studentVaccinationDTO) {
        var query = getVaccination_records_by_id(studentVaccinationDTO.getRecordId());
        if (query != null) {
            query.setVaccinationRecordID(studentVaccinationDTO.getRecordId());
            query.setObservation_notes(studentVaccinationDTO.getObservationNotes());
            query.setObservation_time(studentVaccinationDTO.getObservationTime());
            query.setStatus(studentVaccinationDTO.getStatus());
            return entityManager.merge(studentVaccinationDTO);
        }
        return null;
    }

}
