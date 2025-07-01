package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.SchoolNurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchoolNurseRepository extends JpaRepository<SchoolNurse, Integer> {
    @Query("SELECT s FROM SchoolNurse s")
    public List<SchoolNurse> getAll();

}
