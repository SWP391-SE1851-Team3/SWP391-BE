package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.InterfaceRepo.Post_vaccination_observationsInterFace;
import com.team_3.School_Medical_Management_System.Model.Post_vaccination_observations;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Post_vaccination_observationsRepo implements Post_vaccination_observationsInterFace {

    private EntityManager entityManager;

    @Autowired
    public Post_vaccination_observationsRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Post_vaccination_observations> getPost_vaccination_observations() {
        String sql = "select p from Post_vaccination_observations p";
        return entityManager.createQuery(sql, Post_vaccination_observations.class).getResultList();
    }

    @Override
    public Post_vaccination_observations getPost_vaccination_observation(Integer id) {
        return entityManager.find(Post_vaccination_observations.class, id);

    }

    @Override
    public Post_vaccination_observations addPost_vaccination_observation(Post_vaccination_observations post_vaccination_observation) {
        entityManager.persist(post_vaccination_observation);
        return post_vaccination_observation;
    }

    @Override
    public Post_vaccination_observations updatePost_vaccination_observation(Post_vaccination_observations post_vaccination_observation) {

        var searchId = getPost_vaccination_observation(post_vaccination_observation.getObservation_id());
        if (searchId != null) {
            searchId.setVaccination_records(post_vaccination_observation.getVaccination_records());
            searchId.setUpdatedByNurse(post_vaccination_observation.getUpdatedByNurse());
            searchId.setObservation_id(post_vaccination_observation.getObservation_id());
            searchId.setObservation_time(post_vaccination_observation.getObservation_time());
            searchId.setNotes(post_vaccination_observation.getNotes());
            searchId.setStatus(post_vaccination_observation.getStatus());
            searchId.setSymptoms(post_vaccination_observation.getSymptoms());
            searchId.setSeverity(post_vaccination_observation.getSeverity());
            entityManager.merge(searchId);
            return searchId;
        } else {
            return null;
        }
    }

    @Override
    public void deleteByVaccinationRecordId(Integer id) {
        String jpql = "DELETE FROM Post_vaccination_observations p WHERE p.vaccination_records.VaccinationRecordID = :recordId";
        entityManager.createQuery(jpql)
                .setParameter("recordId", id)
                .executeUpdate();
    }

}
