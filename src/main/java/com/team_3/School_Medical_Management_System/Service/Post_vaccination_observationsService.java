package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.*;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.Post_vaccination_observationsServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class Post_vaccination_observationsService implements Post_vaccination_observationsServiceInterFace {
    private Post_vaccination_observationsInterFace postVaccinationObservationsInterFace;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ParentRepository parentRepo;
    @Autowired
    private Email_Pos_Vaccines_Service emailService;

    @Autowired
    private NotificationsParentService notificationsParentService;

    @Autowired
    private VaccinationRepo vaccinationRepo;

    @Autowired
    private PostRepository post_vaccination_observationsRepo;

    @Autowired
    private com.team_3.School_Medical_Management_System.InterfaceRepo.SchoolNurseRepository SchoolNurseRepository;






    @Autowired
    public Post_vaccination_observationsService(Post_vaccination_observationsInterFace postVaccinationObservationsInterFace) {
        this.postVaccinationObservationsInterFace = postVaccinationObservationsInterFace;
    }

    @Override
    public List<Post_vaccination_observationsDTO> getPost_vaccination_observations() {
        var list = postVaccinationObservationsInterFace.getPost_vaccination_observations();
        return list.stream().map(TransferModelsDTO :: MappingVaccination_observations).collect(Collectors.toList());
    }

    @Override
    public Post_vaccination_observationsDTO getPost_vaccination_observation(Integer id) {
       return TransferModelsDTO.MappingVaccination_observations(postVaccinationObservationsInterFace.getPost_vaccination_observation(id));
    }

    @Override
    public Post_vaccination_observationsDTO addPost_vaccination_observation(Post_vaccination_observationsDTO dto) {
        var addPost_vaccination_observation =
                postVaccinationObservationsInterFace.addPost_vaccination_observation(TransferModelsDTO.MappingVaccination_observationsDTO(dto));
        return TransferModelsDTO.MappingVaccination_observations(addPost_vaccination_observation);
    }

    @Override
    public Post_vaccination_observations_edit_DTO updatePost_vaccination_observation(Post_vaccination_observations_edit_DTO dto) {
        var updatePost_vaccination_observation =
                postVaccinationObservationsInterFace.updatePost_vaccination_observation(TransferModelsDTO.MappingPost_vaccination_observations(dto));
        return TransferModelsDTO.MappingPost_vaccination_observations_edit_DTO(updatePost_vaccination_observation);
    }

    @Override
    public Post_vaccination_observations_SendForParent_DTO addPost_vaccination_observationByEmail(Post_vaccination_observations_SendForParent_DTO dto) {
        // 1. Tạo entity từ DTO
        Post_vaccination_observations record = new Post_vaccination_observations();
        record.setNotes(dto.getNotes());
        record.setSymptoms(dto.getSymptoms());
        record.setSeverity(dto.getSeverity());
        record.setObservation_time(dto.getObservation_time());
        record.setStatus(dto.getStatus());

        // 2. Lấy Vaccination record + học sinh
        Vaccination_records vaccination = vaccinationRepo.findFullRecordById(dto.getVaccinationRecordID())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ tiêm chủng với ID: " + dto.getVaccinationRecordID()));

        if (vaccination.getStudent() == null) {
            throw new RuntimeException("Hồ sơ tiêm chủng không chứa thông tin học sinh.");
        }
        Student student = vaccination.getStudent();
        record.setVaccination_records(vaccination);

        // 3. Lấy nurse từ DB
        SchoolNurse nurse = SchoolNurseRepository.findById(dto.getCreateNurseID())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên y tế"));
        record.setCreatedByNurse(nurse);

        if (dto.getEditNurseID() != null) {
            SchoolNurse nurses = SchoolNurseRepository.findById(dto.getEditNurseID())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên y tế"));
            record.setUpdatedByNurse(nurses);
        }

        // 4. Lấy phụ huynh từ học sinh
        Parent parent = parentRepo.GetParentByStudentId(student.getStudentID());
        if (parent == null) {
            throw new RuntimeException("Không tìm thấy phụ huynh của học sinh ID: " + student.getStudentID());
        }

        // 5. Lưu vào DB
        post_vaccination_observationsRepo.save(record);

        // 6. Tạo nội dung email
        String studentName = student.getFullName();
        String parentName = parent.getFullName();
        String nurseName = nurse.getFullName();
        String className = student.getClassName();
        Integer parentID = parent.getParentID();

        String title = "Học Sinh Đã Bị Phản Ứng Sau Khi Tiêm Chủng Tại Trường " + studentName + " (" + className + ")";
        String content = String.format("""
        <p><strong>Kính gửi phụ huynh %s,</strong></p>
        <p>Nhà trường đã nhập thông tin hồ sơ phản ứng sau tiêm cho học sinh <b>%s (%s)</b>.</p>
        <ul>
            <li><strong>Triệu chứng:</strong> %s</li>
            <li><strong>Mức độ:</strong> %s</li>
            <li><strong>Ghi chú:</strong> %s</li>
            <li><strong>Thời gian theo dõi:</strong> %s</li>
            <li><strong>Trạng thái:</strong> %s</li>
        </ul>
        <p>Thông tin được ghi nhận bởi nhân viên y tế: <strong>%s</strong></p>
        <p>Vui lòng đăng nhập hệ thống để xem chi tiết.</p>
        """,
                parentName,
                studentName, className,
                dto.getSymptoms(),
                dto.getSeverity(),
                dto.getNotes(),
                dto.getObservation_time() != null ? dto.getObservation_time().toString() : "Không có",
                dto.getStatus(),
                nurseName
        );

        // 7. Gửi email + thông báo
        Integer notificationId = notificationsParentService.createAutoNotification(parentID, title, content);
        emailService.sendHtmlNotificationEmail(parent, title, content, notificationId);

        // 8. Trả về DTO đã mapping đầy đủ
        Post_vaccination_observations_SendForParent_DTO result =
                TransferModelsDTO.MappingPost_vaccination_observations_SendForParent_DTO(record);
        result.setParentID(parentID);
        result.setEmail(parent.getEmail());

        return result;
    }

    @Override
    public Post_vaccination_observations_edit_Update_SendParent_DTO updateAndResendEmail(Integer recordId, Post_vaccination_observations_edit_Update_SendParent_DTO dto) {
        // 1. Tìm bản ghi gốc nếu cần, hoặc tạo mới object
        Post_vaccination_observations record = new Post_vaccination_observations();
        record.setNotes(dto.getNotes());
        record.setSymptoms(dto.getSymptoms());
        record.setSeverity(dto.getSeverity());
        record.setObservation_time(dto.getObservation_time());
        record.setStatus(dto.getStatus());
        Vaccination_records vaccination = vaccinationRepo.findFullRecordById(dto.getVaccinationRecordID())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ tiêm chủng với ID: " + dto.getVaccinationRecordID()));

        if (vaccination.getStudent() == null) {
            throw new RuntimeException("Hồ sơ tiêm chủng không chứa thông tin học sinh.");
        }
        Student student = vaccination.getStudent();
        record.setVaccination_records(vaccination);

        // 3. Lấy nurse từ DB
        SchoolNurse nurse = SchoolNurseRepository.findById(dto.getEditNurseID())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên y tế"));
        record.setUpdatedByNurse(nurse);

        // 4. Lấy thông tin học sinh từ vaccination record


        // 5. Lấy phụ huynh từ học sinh
        Parent parent = parentRepo.GetParentByStudentId(dto.getStudentID());
        if (parent == null) {
            throw new RuntimeException("Không tìm thấy phụ huynh của học sinh ID: " + dto.getStudentID());
        }
        // 6. Lưu vào DB
        post_vaccination_observationsRepo.save(record);

        // 7. Tạo nội dung email
        String studentName = student.getFullName();
        String parentName = parent.getFullName();
        String nurseName = nurse.getFullName();
        String className = student.getClassName();
        Integer parentID = parent.getParentID();


        String title = "Cập nhật danh sách phản ứng thuốc  của học sinh " + studentName + " (" + className + ")";
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
                dto.getStatus(),
                nurseName
        );

        // 6. Gửi thông báo & email
        Integer notificationId = notificationsParentService.createAutoNotification(parentID, title, content);
        emailService.sendHtmlNotificationEmail(parent, title, content, notificationId);

        // 7. Trả về DTO (phân biệt rõ createNurse vs editNurse)
        SchoolNurse createNurse = SchoolNurseRepository.findById(dto.getCreateNurseID())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên y tế"));

        Post_vaccination_observations_edit_Update_SendParent_DTO result = TransferModelsDTO.Post_vaccination_observations_edit_Update_SendParent_DTO(
                record

        );
        result.setParentID(parentID);
        result.setEmail(parent.getEmail());
        return result;
    }





}
