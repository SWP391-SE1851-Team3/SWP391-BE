package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepositories extends JpaRepository<Student, Integer> {
    List<Student> findByClassName(String className);

    @Query("SELECT DISTINCT s.className FROM Student s")
    List<String> findAllClassNames();
}
