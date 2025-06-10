package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.VaccinesInterFace;
import com.team_3.School_Medical_Management_System.Model.Vaccines;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VaccinesRepo implements VaccinesInterFace {
    private EntityManager entityManager;

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
        // Tìm vaccine theo tên
        var existingVaccine = entityManager.createQuery("SELECT v FROM Vaccines v WHERE v.Name = :name", Vaccines.class)
                .setParameter("name", vaccineDetails.getName()) // Set tên vaccine cần tìm
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        if (existingVaccine == null) {
            return null; // Nếu không tìm thấy vaccine theo tên
        }

        // Cập nhật thông tin vaccine
        existingVaccine.setName(vaccineDetails.getName());
        existingVaccine.setManufacturer(vaccineDetails.getManufacturer());
        existingVaccine.setDescription(vaccineDetails.getDescription());
        existingVaccine.setRecommended_ages(vaccineDetails.getRecommended_ages());
        existingVaccine.setDoses_required(vaccineDetails.getDoses_required());

        return entityManager.merge(existingVaccine);
    }

}
