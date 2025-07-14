package com.team_3.School_Medical_Management_System.InterFaceSerivce;

import com.team_3.School_Medical_Management_System.Model.Consent_forms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsentFormsRepos extends JpaRepository<Consent_forms, Integer> {

}