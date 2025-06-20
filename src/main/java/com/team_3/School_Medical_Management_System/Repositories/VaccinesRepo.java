package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.DTO.VaccinesDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.VaccinesInterFace;
import com.team_3.School_Medical_Management_System.Model.Vaccines;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VaccinesRepo implements VaccinesInterFace {
    private EntityManager entityManager;
    @Autowired
    private SchoolNurseRepo schoolNurseRepo;

    @Autowired
    public VaccinesRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Vaccines> GetAllVaccines() {
        String sql = "SELECT v FROM Vaccines v";
        return entityManager.createQuery(sql, Vaccines.class).getResultList();
    }

    @Override
    public Vaccines GetVaccineByVaccineName(String vaccineName) {
        String jpql = "SELECT v FROM Vaccines v WHERE v.Name LIKE :vaccineName";
        return entityManager.createQuery(jpql, Vaccines.class)
                .setParameter("vaccineName", "%" + vaccineName + "%")
                .getSingleResult();
    }

    @Override
    public Vaccines GetVaccineByVaccineId(int VaccineId) {
        return entityManager.find(Vaccines.class, VaccineId);
    }

    @Override
    public Vaccines AddVaccine(Vaccines vaccine) {
         entityManager.persist(vaccine);
         return vaccine;
    }

    @Override
    public Vaccines UpdateVaccine(Vaccines vaccineDetails) {
        var existingVaccine = GetVaccineByVaccineId(vaccineDetails.getVaccine_id());
        if (existingVaccine == null) {
            return null; // Nếu không tìm thấy vaccine theo tên
        }
        existingVaccine.setVaccine_id(vaccineDetails.getVaccine_id());
        existingVaccine.setName(vaccineDetails.getName());
        existingVaccine.setManufacturer(vaccineDetails.getManufacturer());
        existingVaccine.setDescription(vaccineDetails.getDescription());
        existingVaccine.setRecommended_ages(vaccineDetails.getRecommended_ages());
        existingVaccine.setVaccine_updated_at(vaccineDetails.getVaccine_updated_at());
        existingVaccine.setVaccine_created_at(vaccineDetails.getVaccine_created_at());
        existingVaccine.setScheduled_date(vaccineDetails.getScheduled_date());
        existingVaccine.setQuantity_received(vaccineDetails.getQuantity_received());
        existingVaccine.setNurse(vaccineDetails.getNurse());
        existingVaccine.setLocation(vaccineDetails.getLocation());
        existingVaccine.setNotes(vaccineDetails.getNotes());
        existingVaccine.setStatus(vaccineDetails.getStatus());
        return entityManager.merge(existingVaccine);
    }
}
