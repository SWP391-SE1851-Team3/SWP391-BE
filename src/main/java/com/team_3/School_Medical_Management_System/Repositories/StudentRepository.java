package com.team_3.School_Medical_Management_System.Repositories;

import com.team_3.School_Medical_Management_System.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
