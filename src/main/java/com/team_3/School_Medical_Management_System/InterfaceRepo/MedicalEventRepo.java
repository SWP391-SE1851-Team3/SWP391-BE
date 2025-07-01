package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalEvent;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalEventRepo extends JpaRepository<MedicalEvent, Integer> {

 @Modifying
 @Query("SELECT m FROM MedicalEvent m WHERE m.parent.ParentID = :parentId")
 public List<MedicalEvent> getByParentId(@Param("parentId") int parentId);

 @Modifying
@Query("DELETE FROM MedicalEvent m WHERE m.parent.ParentID = :parentId")
public void deleteByParentID(@Param("parentId") int parentId);

 @Modifying
 @Query("UPDATE MedicalEvent m SET m.parent = NULL WHERE m.parent.ParentID = :parentID")
 public void setNullParentIDByParentID(@Param("parentID") int parentID);


}
