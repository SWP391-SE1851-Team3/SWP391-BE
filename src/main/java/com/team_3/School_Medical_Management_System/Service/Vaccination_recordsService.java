package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.*;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.Vaccination_recordsServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import com.team_3.School_Medical_Management_System.configuration.EmailConfig;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class Vaccination_recordsService implements Vaccination_recordsServiceInterFace {
    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private NotificationsMedicalEventDetailsRepository notificationsMedicalEventDetailsRepository;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ParentRepository parentRepo;
    @Autowired
    private EmailVaccinesService emailService;

    @Autowired
    private NotificationsParentService notificationsParentService;

    @Autowired
    private VaccinationRepo vaccinationRepo;

    @Autowired
    private SchoolNurseRepository SchoolNurseRepository;


    private Vaccination_recordsInterFace vaccination_recordsInterFace;


    @Autowired
    private VaccineBatchRepository vaccineBatchRepo;


    @Autowired
    private ConsentRepository cosentRepo;

    @Autowired
    private VaccinationRecordRepository vaccinationRecordRepository;


    @Autowired
    public Vaccination_recordsService(Vaccination_recordsInterFace vaccination_recordsInterFace) {
        this.vaccination_recordsInterFace = vaccination_recordsInterFace;
    }

    public List<Vaccination_recordsDTO> getVaccination_records() {
        var list = vaccination_recordsInterFace.getVaccination_records();
        return list.stream()
                .map(TransferModelsDTO::MappingVaccinationRecords)
                .collect(Collectors.toList());
    }

    @Override
    public Vaccination_recordsDTO addVaccination_records(Vaccination_recordsDTO vaccinationRecordsDTO) {
        Vaccination_records p = vaccination_recordsInterFace.addVaccination_records(
                TransferModelsDTO.MappingVaccinationRecordsDTO(vaccinationRecordsDTO)
        );
        return TransferModelsDTO.MappingVaccinationRecords(p);
    }

    @Override
    public void deleteVaccination_records(int id) {
        vaccination_recordsInterFace.deleteVaccination_records(id);
    }

    @Override
    public Vaccination_recordsDTO getVaccination_records_by_id(int id) {
        var vaccination_records_by_id = vaccination_recordsInterFace.getVaccination_records_by_id(id);
        if (vaccination_records_by_id == null) {
            throw new RuntimeException("Vaccination_records_by_id is null");
        } else {
            return TransferModelsDTO.MappingVaccinationRecords(vaccination_records_by_id);

        }
    }

    @Override
    public Vaccination_records_edit_DTO updateVaccination_records(Vaccination_records_edit_DTO vaccination_records_edit_DTO) {
        var updateReocord = vaccination_recordsInterFace.updateVaccination_records(TransferModelsDTO.MappingVaccinationRecordsEditDTO(vaccination_records_edit_DTO));
        return TransferModelsDTO.MappingVaccinationRecordsEdit(updateReocord);
    }

    @Override
    public List<Vaccination_recordsDTO> getVaccination_recordsByStudentId(int studentId) {
        var vaccination_recordsByStudentId = vaccination_recordsInterFace.getVaccination_recordsByStudentId(studentId);
        if (vaccination_recordsByStudentId == null || vaccination_recordsByStudentId.isEmpty()) {
            throw new RuntimeException("vaccination_records_student is null");
        } else {
            return vaccination_recordsByStudentId.stream()
                    .map(TransferModelsDTO::MappingVaccinationRecords)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Vaccination_records_SentParent_DTO createEmail(Vaccination_records_SentParent_DTO dto) {
        Optional<Consent_forms> consentOpt = cosentRepo.findByStudentIdAndBatchIdAndStatus(
                dto.getStudentId(),
                dto.getVaccineBatchId(),
                "đã phê duyệt"
        );

        Consent_forms consent = consentOpt.orElseThrow(() ->
                new RuntimeException("Chưa có phiếu đồng ý được phê duyệt cho học sinh này"));

        if (!"Đồng ý".equalsIgnoreCase(consent.getIsAgree())) {
            throw new RuntimeException("Phụ huynh chưa đồng ý tiêm chủng");
        }

        Student student = consent.getStudent();
        Vaccine_Batches vaccine_Batches = consent.getVaccineBatches();

        // 1. Tạo entity từ DTO
        Vaccination_records record = new Vaccination_records();
        record.setNotes(dto.getNotes());
        record.setSymptoms(dto.getSymptoms());
        record.setSeverity(dto.getSeverity());
        record.setObservation_notes(dto.getObservation_notes());
        record.setObservation_time(dto.getObservation_time());
        record.setStatus(dto.getStatus());
        record.setConsentForm(consent);
        record.setStudent(student);
        record.setVaccineBatches(vaccine_Batches);

        // 3. Lưu hồ sơ tiêm chủng
        vaccinationRepo.save(record);

        // 4. Tìm phụ huynh
        Parent parent = parentRepo.GetParentByStudentId(dto.getStudentId());
        if (parent == null) {
            throw new RuntimeException("Không tìm thấy phụ huynh của học sinh ID: " + dto.getStudentId());
        }
        // 5. Trả về DTO
        Vaccination_records_SentParent_DTO result = TransferModelsDTO.MappingVaccination_records_SentParent(record);
        result.setParentID(parent.getParentID());
        result.setEmail(parent.getEmail());
        return result;
    }

    @Override
    public Vaccination_records_SentParent_Edit_DTO updateAndResendEmail(
            Integer recordId,
            Vaccination_records_SentParent_Edit_DTO dto
    ) {
        return vaccinationRecordRepository.findById(recordId).map(record -> {

            // 1. Tìm phiếu đồng ý đã phê duyệt
            Consent_forms consent = cosentRepo.findByStudentIdAndBatchIdAndStatus(
                    dto.getStudentId(), dto.getVaccineBatchId(), "đã phê duyệt"
            ).orElseThrow(() -> new RuntimeException("Chưa có phiếu đồng ý được phê duyệt"));

            if (!"Đồng ý".equalsIgnoreCase(consent.getIsAgree())) {
                throw new RuntimeException("Phụ huynh chưa đồng ý tiêm chủng");
            }

            boolean isFirstTime = (record.getCreatedByNurse() == null);

            SchoolNurse currentNurse = SchoolNurseRepository.findById(dto.getEditNurseID())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên y tế"));

            // ✅ Sửa: Luôn cập nhật nếu DTO có dữ liệu (không kiểm tra null trong record nữa)
            if (dto.getNotes() != null) {
                record.setNotes(dto.getNotes());
            }
            if (dto.getSymptoms() != null) {
                record.setSymptoms(dto.getSymptoms());
            }
            if (dto.getSeverity() != null) {
                record.setSeverity(dto.getSeverity());
            }
            if (dto.getObservation_notes() != null) {
                record.setObservation_notes(dto.getObservation_notes());
            }
            if (dto.getObservation_time() != null) {
                record.setObservation_time(dto.getObservation_time());
            }
            if (dto.getStatus() != null) {
                record.setStatus(dto.getStatus());
            }
            if (record.getConsentForm() == null) {
                record.setConsentForm(consent);
            }

            String action, emailAction;
            if (isFirstTime) {
                record.setCreatedByNurse(currentNurse);
                action = "tạo mới";
                emailAction = "tạo mới";
            } else {
                record.setUpdatedByNurse(currentNurse);
                action = "cập nhật";
                emailAction = "cập nhật";
            }

            vaccinationRepo.saveAndFlush(record);

            Student student = consent.getStudent();
            Parent parent = parentRepo.GetParentByStudentId(student.getStudentID());
            if (parent == null) throw new RuntimeException("Không tìm thấy phụ huynh");

            String nurseName = currentNurse.getFullName();
            String title = String.format("%s hồ sơ tiêm chủng của học sinh %s (%s)",
                    isFirstTime ? "Tạo mới" : "Cập nhật",
                    student.getFullName(),
                    student.getClassName());

            String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

            StringBuilder contentBuilder = new StringBuilder();
            contentBuilder.append("<p><strong>Kính gửi phụ huynh ").append(parent.getFullName()).append(",</strong></p>\n");
            contentBuilder.append("<p>Hồ sơ tiêm chủng của con em đã được <strong>").append(emailAction).append("</strong> với thông tin sau:</p>\n");
            contentBuilder.append("<ul>\n");
            contentBuilder.append("    <li><strong>Triệu chứng:</strong> ").append(record.getSymptoms() != null ? record.getSymptoms() : "Không có").append("</li>\n");
            contentBuilder.append("    <li><strong>Mức độ:</strong> ").append(record.getSeverity() != null ? record.getSeverity() : "Không có").append("</li>\n");
            contentBuilder.append("    <li><strong>Ghi chú:</strong> ").append(record.getNotes() != null ? record.getNotes() : "Không có").append("</li>\n");
            contentBuilder.append("    <li><strong>Thời gian theo dõi:</strong> ").append(record.getObservation_time() != null ? record.getObservation_time().toString() : "Không có").append("</li>\n");
            contentBuilder.append("    <li><strong>Trạng thái:</strong> ").append(record.getStatus() != null ? record.getStatus() : "Không có").append("</li>\n");
            contentBuilder.append("    <li><strong>Ghi chú theo dõi:</strong> ").append(record.getObservation_notes() != null ? record.getObservation_notes() : "Không có").append("</li>\n");
            contentBuilder.append("</ul>\n");
            contentBuilder.append("<p>Nhân viên y tế phụ trách: <strong>").append(nurseName).append("</strong></p>\n");
            contentBuilder.append("<p><em>Thao tác được thực hiện vào: ").append(currentTime).append("</em></p>");

            String content = contentBuilder.toString();

            Integer notificationId = notificationsParentService.createAutoNotification(
                    parent.getParentID(), title, content);
            emailService.sendHtmlNotificationEmail(parent, title, content, notificationId);

            Vaccination_records_SentParent_Edit_DTO result =
                    TransferModelsDTO.MappingVaccination_records_SentParent_Edit(record);

            if (record.getCreatedByNurse() != null) {
                result.setCreateNurseID(record.getCreatedByNurse().getNurseID());
                result.setCreateNurseName(record.getCreatedByNurse().getFullName());
            }

            if (isFirstTime) {
                result.setEditNurseID(record.getCreatedByNurse().getNurseID());
                result.setEditNurseName(record.getCreatedByNurse().getFullName());
            } else {
                result.setEditNurseID(record.getUpdatedByNurse().getNurseID());
                result.setEditNurseName(record.getUpdatedByNurse().getFullName());
            }

            result.setParentID(parent.getParentID());
            result.setEmail(parent.getEmail());

            return result;

        }).orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ tiêm chủng"));
    }

    @Override
    public List<StudentVaccinationDTO> getStudentFollowedbyNurse() {
        return vaccination_recordsInterFace.getStudentFollowedbyNurse();
    }

    @Override
    public StudentVaccinationDTO updateStudentFollowedbyNurse(StudentVaccinationDTO studentVaccinationDTO) {
       return vaccination_recordsInterFace.updateStudentFollowedbyNurse(studentVaccinationDTO);
    }


}