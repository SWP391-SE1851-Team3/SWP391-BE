package com.team_3.School_Medical_Management_System.DTO;

import java.util.Date;
import java.util.List;

public class MedicationSubmissionInfoDTO {
    private String studentName;
    private Date submissionDate;
    private String status;
    private List<MedicationDetailDTO> medicationDetails;

    // Constructors
    public MedicationSubmissionInfoDTO() {}

    public MedicationSubmissionInfoDTO(String studentName, Date submissionDate, String status, List<MedicationDetailDTO> medicationDetails) {
        this.studentName = studentName;
        this.submissionDate = submissionDate;
        this.status = status;
        this.medicationDetails = medicationDetails;
    }

    // Getters and Setters
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MedicationDetailDTO> getMedicationDetails() {
        return medicationDetails;
    }

    public void setMedicationDetails(List<MedicationDetailDTO> medicationDetails) {
        this.medicationDetails = medicationDetails;
    }
}

