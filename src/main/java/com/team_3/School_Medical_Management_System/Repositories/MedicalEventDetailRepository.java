package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.MedicalEventDetail;
import com.team_3.School_Medical_Management_System.Model.MedicalEventDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicalEventDetailRepository extends JpaRepository<MedicalEventDetail, MedicalEventDetailId> {

}
