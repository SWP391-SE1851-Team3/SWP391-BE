package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Vaccine_Batches;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VaccineBatchRepository  extends JpaRepository<Vaccine_Batches, Integer> {
    Optional<Vaccine_Batches> findByDot(String dot);

}
