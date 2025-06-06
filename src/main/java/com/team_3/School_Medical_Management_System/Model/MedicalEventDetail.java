package com.team_3.School_Medical_Management_System.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// lưu thông tin chi
@Entity
@Getter
@Setter
@ToString
@IdClass(MedicalEventDetailId.class)
public class MedicalEventDetail {

    @Id
    @ManyToOne
    @JoinColumn(name = "StudentID")
    private Student studentID;

    @Id
    @ManyToOne
    @JoinColumn(name = "EventID")
    private MedicalEvent event;

    @Column(columnDefinition = "nvarchar(255)", nullable = false)
    private String note; // Ghi chú (ví dụ: "Đã cho uống paracetamol")

    @Column(columnDefinition = "nvarchar(255)", nullable = false)
    private String result; // Kết quả (ví dụ: "Học sinh ổn định")

    @Column(columnDefinition = "nvarchar(255)", nullable = false)
    private String processingStatus;

    public MedicalEventDetail() {
    }

    public MedicalEventDetail(Student student, MedicalEvent event, String note, String result, String processingStatus) {
        this.studentID = student;
        this.event = event;
        this.note = note;
        this.result = result;
        this.processingStatus = processingStatus;
    }
}
