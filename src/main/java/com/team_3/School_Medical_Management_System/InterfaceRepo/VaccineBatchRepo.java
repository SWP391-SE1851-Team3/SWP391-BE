package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Vaccination_records;
import com.team_3.School_Medical_Management_System.Model.Vaccine_Batches;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineBatchRepo  extends JpaRepository<Vaccine_Batches, Integer> {

}
