package com.team_3.School_Medical_Management_System.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team_3.School_Medical_Management_System.DTO.MedicationDetailDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicationDetailsExtendedDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicationSubmissionDTO;
import com.team_3.School_Medical_Management_System.DTO.MedicationSubmissionInfoDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.MedicationSubmissionServiceInterface;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.SchoolNurseServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.ConfirmMedicationSubmissionInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.MedicationSubmissionInterFace;
import com.team_3.School_Medical_Management_System.Model.ConfirmMedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.MedicationDetail;
import com.team_3.School_Medical_Management_System.Model.MedicationSubmission;
import com.team_3.School_Medical_Management_System.Model.Student;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MedicationSubmissionService implements MedicationSubmissionServiceInterface {

    @Autowired
    private MedicationSubmissionInterFace medicationSubmissionInterFace;
    @Autowired
    private ConfirmMedicationSubmissionInterFace confirmMedicationSubmissionInterFace;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SchoolNurseServiceInterFace schoolNurseService;

    @Override
    public MedicationSubmission submitMedication(MedicationSubmissionDTO medicationSubmissionDTO) {
        // Create and save the medication submission as before
        MedicationSubmission submission = new MedicationSubmission();
        submission.setParentId(medicationSubmissionDTO.getParentId());
        submission.setStudentId(medicationSubmissionDTO.getStudentId());
        submission.setSubmissionDate(medicationSubmissionDTO.getMedicationDate());

        // Không cần xử lý ảnh ở đây, sẽ upload riêng sau

        // Convert MedicationDetailDTO list to MedicationDetail
        if (medicationSubmissionDTO.getMedicationDetails() != null && !medicationSubmissionDTO.getMedicationDetails().isEmpty()) {
            List<MedicationDetail> medicationDetails = medicationSubmissionDTO.getMedicationDetails().stream()
                    .map(detailDTO -> {
                        MedicationDetail detail = new MedicationDetail();
                        detail.setMedicineName(detailDTO.getMedicineName());
                        detail.setDosage(detailDTO.getDosage());
                        detail.setTimeToUse(detailDTO.getTimeToUse());
                        detail.setNote(detailDTO.getNote());
                        detail.setMedicationSubmission(submission);
                        return detail;
                    })
                    .collect(Collectors.toList());

            submission.setMedicationDetails(medicationDetails);
        }
        // Save the medication submission
        MedicationSubmission savedSubmission = medicationSubmissionInterFace.save(submission);

        // Create and save a confirmation record with PENDING status
        ConfirmMedicationSubmission confirmation = new ConfirmMedicationSubmission();
        confirmation.setMedicationSubmissionId(savedSubmission.getMedicationSubmissionId());
        confirmation.setStatus("Chờ phản hồi"); // Using string literal instead of constant

        // Note: nurseId and evidence will be updated later when a nurse processes this

        confirmMedicationSubmissionInterFace.save(confirmation);

        return savedSubmission;
    }

    @Override
    public void cancelMedicationSubmission(int submissionId) {
        // Find the medication submission using your existing interface
        MedicationSubmission submission = medicationSubmissionInterFace.findById(submissionId)
                .orElseThrow(() -> new IllegalArgumentException("Medication submission not found"));

        // Find the confirmation using your interface and handle Optional properly
        Optional<ConfirmMedicationSubmission> confirmationOptional = confirmMedicationSubmissionInterFace
                .findByMedicationSubmissionId(submissionId);

        if (!confirmationOptional.isPresent() ||

                confirmationOptional.get().getStatus().equalsIgnoreCase("Chờ phản hồi")) {


            // If confirmation exists, delete it first
            confirmationOptional.ifPresent(confirmation ->
                    confirmMedicationSubmissionInterFace.delete(confirmation));

            // Then delete the medication submission
            medicationSubmissionInterFace.delete(submission);
        } else {
            throw new IllegalStateException("Cannot cancel medication submission that has been processed or administered");
        }
    }

    public List<MedicationDetail> getDetailsBySubmissionId(int submissionId) {
        MedicationSubmission submission = medicationSubmissionInterFace.findById(submissionId)
                .orElseThrow(() -> new EntityNotFoundException("Medication submission not found with id: " + submissionId));
        return submission.getMedicationDetails();
    }

    @Override
    public List<MedicationSubmission> getAllMedicationSubmissionsByParentId(int parentId) {
        return medicationSubmissionInterFace.findByParentId(parentId);
    }

    @Override
    public List<MedicationSubmission> findAllSubmissions() {
        // Implement according to your business logic
        return medicationSubmissionInterFace.findAllSubmissions();
    }

    @Override
    public List<MedicationSubmissionInfoDTO> getAllMedicationSubmissionInfo() {
        List<MedicationSubmission> submissions = medicationSubmissionInterFace.findAll();
        return submissions.stream().map(submission -> {
            Student student = studentService.getStudent(submission.getStudentId());
            String studentName = student != null ? student.getFullName() : "Unknown";
            String className = student != null ? student.getClassName() : "Unknown";
            List<MedicationDetailDTO> detailDTOs = submission.getMedicationDetails().stream().map(detail -> {
                MedicationDetailDTO dto = new MedicationDetailDTO();
                dto.setMedicineName(detail.getMedicineName());
                dto.setDosage(detail.getDosage());
                dto.setTimeToUse(detail.getTimeToUse());
                dto.setNote(detail.getNote());
                dto.setMedicationDetailId(detail.getMedicationDetailId());
                return dto;
            }).collect(java.util.stream.Collectors.toList());
            String status = "Chờ phản hồi";
            Optional<ConfirmMedicationSubmission> confirmOpt = confirmMedicationSubmissionInterFace.findByMedicationSubmissionId(submission.getMedicationSubmissionId());
            if (confirmOpt.isPresent() && confirmOpt.get().getStatus() != null) {
                status = confirmOpt.get().getStatus();
            }
            java.util.Date submissionDate = null;
            if (submission.getSubmissionDate() != null) {
                submissionDate = java.sql.Timestamp.valueOf(submission.getSubmissionDate());
            }
            int confirmId = confirmOpt.map(ConfirmMedicationSubmission::getConfirmId).orElse(0);
            return new MedicationSubmissionInfoDTO(
                    studentName,
                    submissionDate,
                    status,
                    className,
                    detailDTOs,
                    submission.getStudentId(),
                    submission.getMedicationSubmissionId(),
                    confirmId
            );
        }).collect(java.util.stream.Collectors.toList());
    }

    public List<MedicationSubmissionInfoDTO> getMedicationSubmissionInfoByParentId(int parentId) {
        List<MedicationSubmission> submissions = medicationSubmissionInterFace.findByParentId(parentId);
        return submissions.stream().map(submission -> {
            Student student = studentService.getStudent(submission.getStudentId());
            String studentName = student != null ? student.getFullName() : "Unknown";
            String className = student != null ? student.getClassName() : "Unknown";
            List<MedicationDetailDTO> detailDTOs = submission.getMedicationDetails().stream().map(detail -> {
                MedicationDetailDTO dto = new MedicationDetailDTO();
                dto.setMedicineName(detail.getMedicineName());
                dto.setDosage(detail.getDosage());
                dto.setTimeToUse(detail.getTimeToUse());
                dto.setNote(detail.getNote());
                dto.setMedicationDetailId(detail.getMedicationDetailId());
                return dto;
            }).collect(java.util.stream.Collectors.toList());
            String status = "Chờ phản hồi";
            Optional<ConfirmMedicationSubmission> confirmOpt = confirmMedicationSubmissionInterFace.findByMedicationSubmissionId(submission.getMedicationSubmissionId());
            if (confirmOpt.isPresent() && confirmOpt.get().getStatus() != null) {
                status = confirmOpt.get().getStatus();
            }
            java.util.Date submissionDate = null;
            if (submission.getSubmissionDate() != null) {
                submissionDate = java.sql.Timestamp.valueOf(submission.getSubmissionDate());
            }
            int confirmId = confirmOpt.map(ConfirmMedicationSubmission::getConfirmId).orElse(0);
            return new MedicationSubmissionInfoDTO(
                    studentName,
                    submissionDate,
                    status,
                    className,
                    detailDTOs,
                    submission.getStudentId(),
                    submission.getMedicationSubmissionId(),
                    confirmId
            );
        }).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public MedicationSubmission getMedicationSubmissionById(int submissionId) {
        return medicationSubmissionInterFace.findById(submissionId).orElse(null);
    }

    @Override
    public MedicationDetailsExtendedDTO getDetailsBySubmissionIdExtended(int submissionId) {
        // Lấy thông tin submission
        MedicationSubmission submission = getMedicationSubmissionById(submissionId);
        if (submission == null) {
            throw new EntityNotFoundException("Medication submission not found with id: " + submissionId);
        }

        // Lấy danh sách chi tiết thuốc
        List<MedicationDetail> medicationDetails = submission.getMedicationDetails();
        if (medicationDetails == null || medicationDetails.isEmpty()) {
            throw new EntityNotFoundException("No medication details found for submission id: " + submissionId);
        }

        // Lấy thông tin về lớp học của học sinh
        Student student = studentService.getStudent(submission.getStudentId());
        String studentClass = student != null ? student.getClassName() : null;

        // Lấy thông tin về y tá
        String nurseName = null;
        // Tìm ConfirmMedicationSubmission để lấy nurseId
        Optional<ConfirmMedicationSubmission> confirmOpt =
                confirmMedicationSubmissionInterFace.findByMedicationSubmissionId(submissionId);

        if (confirmOpt.isPresent() && confirmOpt.get().getNurseId() != null) {
            int nurseId = confirmOpt.get().getNurseId();
            nurseName = schoolNurseService.getNurseNameById(nurseId);
        }

        // Chuyển đổi danh sách MedicationDetail thành danh sách MedicationDetailDTO
        List<MedicationDetailDTO> medicationDetailDTOs = medicationDetails.stream()
                .map(detail -> {
                    MedicationDetailDTO dto = new MedicationDetailDTO();
                    dto.setMedicineName(detail.getMedicineName());
                    dto.setDosage(detail.getDosage());
                    dto.setTimeToUse(detail.getTimeToUse());
                    dto.setNote(detail.getNote());
                    dto.setMedicationDetailId(detail.getMedicationDetailId());
                    return dto;
                })
                .collect(Collectors.toList());

        // Tạo đối tượng MedicationDetailsExtendedDTO với cấu trúc mới
        MedicationDetailsExtendedDTO detailsDTO = new MedicationDetailsExtendedDTO(
                submission.getMedicationSubmissionId(),
                submission.getParentId(),
                submission.getStudentId(),
                null, // Không trả về medicineImage trong DTO này, sẽ có endpoint riêng để lấy ảnh
                nurseName,
                studentClass,
                medicationDetailDTOs,
                submission.getSubmissionDate()
        );

        return detailsDTO;
    }

    @Override
    public MedicationSubmission updateMedicationSubmission(MedicationSubmission medicationSubmission) {
        // Kiểm tra submission có tồn tại không
        MedicationSubmission existingSubmission = medicationSubmissionInterFace.findById(medicationSubmission.getMedicationSubmissionId())
                .orElseThrow(() -> new EntityNotFoundException("Medication submission not found with id: " + medicationSubmission.getMedicationSubmissionId()));

        // Cập nhật field ảnh nếu có
        if (medicationSubmission.getMedicineImage() != null) {
            existingSubmission.setMedicineImage(medicationSubmission.getMedicineImage());
        }

        // Lưu và trả về submission đã cập nhật
        return medicationSubmissionInterFace.save(existingSubmission);
    }
}
