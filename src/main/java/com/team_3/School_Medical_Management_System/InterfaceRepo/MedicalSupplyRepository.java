package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.MedicalSupply;
import com.team_3.School_Medical_Management_System.Model.Vaccine_Batches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicalSupplyRepository extends JpaRepository<MedicalSupply, Integer> {
//    List<MedicalSupply> findByVaccineBatch(Vaccine_Batches vaccineBatch);
    @Query("SELECT ms FROM MedicalSupply ms WHERE ms.vaccineType.VaccineTypeID = :typeId")
    List<MedicalSupply> findByVaccineTypeId(@Param("typeId") Integer typeId);
}
