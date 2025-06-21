package com.team_3.School_Medical_Management_System.Repositories;
import com.team_3.School_Medical_Management_System.DTO.VaccineTypeShortDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.Vaccine_TypesInterFace;
import com.team_3.School_Medical_Management_System.Model.Vaccine_Types;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class Vaccine_TypesRepo implements Vaccine_TypesInterFace {
    private EntityManager entityManager;
    @Autowired
    public Vaccine_TypesRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<Vaccine_Types> getVaccine_Types() {
       String jpql = "SELECT v FROM Vaccine_Types v";
       return entityManager.createQuery(jpql, Vaccine_Types.class).getResultList();
    }

    @Override
    public VaccineTypeShortDTO getVaccine_Type(int id) {
        String jpql = "SELECT new com.team_3.School_Medical_Management_System.DTO.VaccineTypeShortDTO(v.id, v.Name) FROM Vaccine_Types v WHERE v.id = :id";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("id", id);
        return (VaccineTypeShortDTO) query.getSingleResult();
    }


    @Override
    public Vaccine_Types updateVaccine_Types(Vaccine_Types vaccine_Types) {
        var p = entityManager.find(Vaccine_Types.class, vaccine_Types.getVaccineTypeID());
        if(p != null) {
            p.setName(vaccine_Types.getName());
            p.setDescription(vaccine_Types.getDescription());
            p.setVaccineTypeID(vaccine_Types.getVaccineTypeID());
            p.setCreated_at(vaccine_Types.getCreated_at());
            p.setUpdated_at(vaccine_Types.getUpdated_at());
            p.setRecommended_ages(vaccine_Types.getRecommended_ages());
            entityManager.merge(p);
        }else {
            return null;
        }
        return vaccine_Types;
    }

    @Override
    public Vaccine_Types deleteVaccine_Types(int id) {
       var p = entityManager.find(Vaccine_Types.class, id);
       if(p != null) {
           entityManager.remove(p);
       }else {
           return null;
       }
       return p;
    }

    @Override
    public Vaccine_Types addVaccine_Types(Vaccine_Types vaccine_Types) {
        entityManager.persist(vaccine_Types);
        return vaccine_Types;
    }

    @Override
    public List<VaccineTypeShortDTO> getVaccine_TypeByName() {
        String jpql = "SELECT new com.team_3.School_Medical_Management_System.DTO.VaccineTypeShortDTO(v.id, v.Name) FROM Vaccine_Types v";
        Query query = entityManager.createQuery(jpql);
        return (List<VaccineTypeShortDTO>) query.getResultList();
    }
}
