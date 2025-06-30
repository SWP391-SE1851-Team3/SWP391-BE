package com.team_3.School_Medical_Management_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SchoolMedicalManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolMedicalManagementSystemApplication.class, args);
	}

}

