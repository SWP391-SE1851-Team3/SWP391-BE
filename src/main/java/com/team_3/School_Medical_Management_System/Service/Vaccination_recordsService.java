package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.Vaccination_recordsDTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccination_records_SentParent_DTO;
import com.team_3.School_Medical_Management_System.DTO.Vaccination_records_edit_DTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.Vaccination_recordsServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.*;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import com.team_3.School_Medical_Management_System.configuration.EmailConfig;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private EmailService emailService;

    @Autowired
    private NotificationsParentService notificationsParentService;

    @Autowired
    private VaccinationRepo vaccinationRepo;

    @Autowired
    private SchoolNurseRepository SchoolNurseRepository;


    private Vaccination_recordsInterFace vaccination_recordsInterFace;

    @Autowired
    public Vaccination_recordsService(Vaccination_recordsInterFace vaccination_recordsInterFace) {
        this.vaccination_recordsInterFace = vaccination_recordsInterFace;
    }

    @Override
    public List<Vaccination_recordsDTO> getVaccination_records() {
        var list = vaccination_recordsInterFace.getVaccination_records();
        return list.stream().map(TransferModelsDTO::MappingVaccinationRecords).collect(Collectors.toList());
    }

    @Override
    public Vaccination_recordsDTO addVaccination_records(Vaccination_recordsDTO vaccinationRecordsDTO) {
        Vaccination_records p = vaccination_recordsInterFace.addVaccination_records(TransferModelsDTO.MappingVaccinationRecordsDTO(vaccinationRecordsDTO));
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
            return vaccination_recordsByStudentId.stream().map(TransferModelsDTO::MappingVaccinationRecords).collect(Collectors.toList());
        }
    }

    @Override
    public Vaccination_records_SentParent_DTO createEmail(Vaccination_records_SentParent_DTO dto) {
        // 1. Tạo entity từ DTO
        Vaccination_records record = new Vaccination_records();
        record.setNotes(dto.getNotes());
        record.setSymptoms(dto.getSymptoms());
        record.setSeverity(dto.getSeverity());
        record.setObservation_notes(dto.getObservation_notes());
        record.setObservation_time(dto.getObservation_time());
        record.setStatus(dto.getStatus());

        // Mapping student entity
        Student student = new Student();
        student.setStudentID(dto.getStudentId());
        record.setStudent(student);

        //Mapping SchoolNurse
        SchoolNurse schoolNurse = new SchoolNurse();
        schoolNurse.setNurseID(dto.getNurseID());
        record.setNurse(schoolNurse);


        // Mapping vaccine batch entity
        Vaccine_Batches batch = new Vaccine_Batches();
        batch.setBatchID(dto.getVaccineBatchId());
        record.setVaccineBatches(batch);

        // 2. Lưu hồ sơ tiêm chủng
        vaccinationRepo.save(record);

        // 3. Tìm phụ huynh qua học sinh
        Parent parent = parentRepo.GetParentByStudentId(dto.getStudentId());
        if (parent == null) {
            throw new RuntimeException("Không tìm thấy phụ huynh của học sinh ID: " + dto.getStudentId());
        }
        SchoolNurse schoolNurse2 = SchoolNurseRepository.findById(dto.getNurseID()).
                orElseThrow(() -> new RuntimeException("Không tìm thấy ý tế"));


        // 4. Lấy tên học sinh đầy đủ
        Student fullStudent = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh"));
        String studentName = fullStudent.getFullName();
        String parentName = parent.getFullName();
        String nurseName = schoolNurse2.getFullName();

        // 5. Tạo nội dung email
        String title = "Hồ sơ tiêm chủng của học sinh " + studentName + " đã được cập nhật";

        String content = String.format("""
                        <p><strong>Kính gửi phụ huynh %s,</strong></p>
                        
                        <p>Nhà trường đã nhập thông tin hồ sơ tiêm chủng cho học sinh <b>%s</b>.</p>
                        
                        <ul>
                            <li><strong>Triệu chứng:</strong> %s</li>
                            <li><strong>Mức độ:</strong> %s</li>
                            <li><strong>Ghi chú:</strong> %s</li>
                            <li><strong>Thời gian theo dõi:</strong> %s</li>
                            <li><strong>Trạng thái:</strong> %s</li>
                            <li><strong>Ghi chú theo dõi:</strong> %s</li>
                        </ul>
                         <p>Thông tin được ghi nhận bởi nhân viên y tế: <strong>%s</strong></p>
                        
                        <p>Vui lòng đăng nhập hệ thống để xem chi tiết.</p>
                        """,
                parentName,
                studentName,
                dto.getSymptoms(),
                dto.getSeverity(),
                dto.getNotes(),
                dto.getObservation_time() != null ? dto.getObservation_time().toString() : "Không có",
                dto.getStatus() != null ? dto.getStatus() : "Đã tiêm",
                dto.getObservation_notes() != null ? dto.getObservation_notes() : "Không có",
                nurseName
        );

        // 6. Gửi thông báo & email
        Integer notificationId = notificationsParentService.createAutoNotification(
                parent.getParentID(), title, content);
        emailService.sendHtmlNotificationEmail(parent, title, content, notificationId);

        // 7. Trả về DTO
        Vaccination_records_SentParent_DTO result = TransferModelsDTO.MappingVaccination_records_SentParent(record);
        result.setParentName(parentName);
        result.setEmail(parent.getEmail());

        return result;
    }

    @Override
    public Vaccination_records_SentParent_DTO updateAndResendEmail(Integer recordId, Vaccination_records_SentParent_DTO dto) {
        // 1. Tìm hồ sơ tiêm chủng
        Vaccination_records record = vaccinationRepo.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ tiêm chủng ID: " + recordId));

        // 2. Cập nhật thông tin
        record.setNotes(dto.getNotes());
        record.setSymptoms(dto.getSymptoms());
        record.setSeverity(dto.getSeverity());
        record.setObservation_notes(dto.getObservation_notes());
        record.setObservation_time(dto.getObservation_time());
        record.setStatus(dto.getStatus());

        vaccinationRepo.save(record);

        //Mapping SchoolNurse
        SchoolNurse schoolNurse = new SchoolNurse();
        schoolNurse.setNurseID(dto.getNurseID());
        record.setNurse(schoolNurse);

        // 3. Lấy thông tin nhân viên y tế
        SchoolNurse nurse = SchoolNurseRepository.findById(dto.getNurseID())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên y tế"));
        String nurseName = nurse.getFullName();

        // 4. Lấy phụ huynh & học sinh
        Parent parent = parentRepo.GetParentByStudentId(dto.getStudentId());
        if (parent == null) {
            throw new RuntimeException("Không tìm thấy phụ huynh");
        }

        Student student = studentRepo.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh"));
        String studentName = student.getFullName();
        String parentName = parent.getFullName();

        // 5. Soạn email
        String title = "Cập nhật hồ sơ tiêm chủng của học sinh " + studentName;

        String content = String.format("""
        <p><strong>Kính gửi phụ huynh %s,</strong></p>
        <p>Nhà trường chân thành xin lỗi vì sự thiếu sót trong thông tin hồ sơ tiêm chủng trước đó.</p>
        <p>Chúng tôi đã <b>cập nhật</b> lại đầy đủ thông tin cho học sinh <b>%s</b> như sau:</p>

        <ul>
            <li><strong>Triệu chứng:</strong> %s</li>
            <li><strong>Mức độ:</strong> %s</li>
            <li><strong>Ghi chú:</strong> %s</li>
            <li><strong>Thời gian theo dõi:</strong> %s</li>
            <li><strong>Trạng thái:</strong> %s</li>
            <li><strong>Ghi chú theo dõi:</strong> %s</li>
        </ul>

        <p>Thông tin được ghi nhận bởi nhân viên y tế: <strong>%s</strong></p>
        <p>Vui lòng đăng nhập hệ thống để xem chi tiết.</p>
        """,
                parentName,
                studentName,
                dto.getSymptoms(),
                dto.getSeverity(),
                dto.getNotes(),
                dto.getObservation_time() != null ? dto.getObservation_time().toString() : "Không có",
                dto.getStatus() != null ? dto.getStatus() : "Đã tiêm",
                dto.getObservation_notes() != null ? dto.getObservation_notes() : "Không có",
                nurseName
        );

        // 6. Gửi thông báo & email
        Integer notificationId = notificationsParentService.createAutoNotification(parent.getParentID(), title, content);
        emailService.sendHtmlNotificationEmail(parent, title, content, notificationId);

        // 7. Trả về DTO
        Vaccination_records_SentParent_DTO result = TransferModelsDTO.MappingVaccination_records_SentParent(record);
        result.setParentName(parentName);
        result.setEmail(parent.getEmail());

        return result;
    }


}