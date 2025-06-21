package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.HealthCheck_Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthCheckScheduleRepository extends JpaRepository<HealthCheck_Schedule, Integer> {
    List<HealthCheck_Schedule> findByStatus(String status);
}
