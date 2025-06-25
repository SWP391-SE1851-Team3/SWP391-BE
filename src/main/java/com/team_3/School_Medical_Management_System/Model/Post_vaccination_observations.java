package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table
@Entity
public class Post_vaccination_observations {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int observation_id;
    private LocalDateTime observation_time;
    private String symptoms;
    private String severity;
    private String notes;
    private String status;
    @ManyToOne
    @JoinColumn(name = "VaccinationRecordID")
    private Vaccination_records vaccination_records;
    @ManyToOne
    @JoinColumn(name = "NurseID")
    private SchoolNurse nurse;

    public Post_vaccination_observations() {
    }

    public Post_vaccination_observations(int observation_id, LocalDateTime observation_time, String symptoms, String severity, String notes, String status, Vaccination_records vaccination_records, SchoolNurse nurse) {
        this.observation_id = observation_id;
        this.observation_time = observation_time;
        this.symptoms = symptoms;
        this.severity = severity;
        this.notes = notes;
        this.status = status;
        this.vaccination_records = vaccination_records;
        this.nurse = nurse;
    }

    public int getObservation_id() {
        return observation_id;
    }

    public void setObservation_id(int observation_id) {
        this.observation_id = observation_id;
    }

    public LocalDateTime getObservation_time() {
        return observation_time;
    }

    public void setObservation_time(LocalDateTime observation_time) {
        this.observation_time = observation_time;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vaccination_records getVaccination_records() {
        return vaccination_records;
    }

    public void setVaccination_records(Vaccination_records vaccination_records) {
        this.vaccination_records = vaccination_records;
    }

    public SchoolNurse getNurse() {
        return nurse;
    }

    public void setNurse(SchoolNurse nurse) {
        this.nurse = nurse;
    }

    @Override
    public String toString() {
        return "Post_vaccination_observations{" +
                "observation_id=" + observation_id +
                ", observation_time=" + observation_time +
                ", symptoms='" + symptoms + '\'' +
                ", severity='" + severity + '\'' +
                ", notes='" + notes + '\'' +
                ", status='" + status + '\'' +
                ", vaccination_records=" + vaccination_records +
                ", nurse=" + nurse +
                '}';
    }
}
