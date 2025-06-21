package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
@IdClass(MedicalEventDetailsId.class)
public class MedicalEventDetails {

    @Id
    @ManyToOne
    @JoinColumn(name = "StudentID")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "EventID")
    private MedicalEvent medicalEvent;



    @Column(columnDefinition = "nvarchar(MAX)", nullable = false)
    private String note; // Ghi chú (ví dụ: "Đã cho uống paracetamol")

    @Column(columnDefinition = "nvarchar(255)", nullable = false)
    private String result; // Kết quả (ví dụ: "Học sinh ổn định")

    @Column(columnDefinition = "nvarchar(255)", nullable = false)
    private String processingStatus;
    @ManyToOne
    @JoinColumn(name = "CreatedByNurseID")
    private SchoolNurse createdByNurse;

    @ManyToOne
    @JoinColumn(name = "UpdatedByNurseID")
    private SchoolNurse updatedByNurse;
    public MedicalEventDetails() {
    }

    public MedicalEventDetails(Student student, MedicalEvent event, String note, String result, String processingStatus) {
        this.student = student;
        this.medicalEvent = event;
        this.note = note;
        this.result = result;
        this.processingStatus = processingStatus;
    }
}
