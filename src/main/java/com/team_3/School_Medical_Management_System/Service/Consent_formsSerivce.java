package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.*;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.ConsentFormsRepos;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.ConsentFormsRepository;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.Consent_formsServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.*;
import com.team_3.School_Medical_Management_System.TransferModelsDTO.TransferModelsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class Consent_formsSerivce implements Consent_formsServiceInterFace {
    private Consent_formsInterFace consent_formsRepo;
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private Vaccine_BatchesRepo vaccinesRepo;

    @Autowired
    private ParentRepo parentRepo;

    @Autowired
    private ConsentFormsRepository consent_forms_repo;

    @Autowired
    private ConsentFormsRepos consent_formsRepos;

    @Autowired
    private StudentRepositories studentRepository;

    @Autowired
    private VaccineBatchRepository vaccineBatchRepository;

    @Autowired
    private EmailSentConsentForm emailService;



    @Autowired
    public Consent_formsSerivce(Consent_formsInterFace consent_formsRepo) {
        this.consent_formsRepo = consent_formsRepo;
    }

    @Autowired
    private VaccinationRecordRepository vaccinationRecordRepository;

    @Autowired
    private NotificationsParentService notificationsParentService;

    @Autowired
    private Vaccination_recordsService vaccinationRecordsService;


    @Override
    public List<Consent_formViewDTO> getConsent_forms() {
        var consent_forms = consent_formsRepo.getConsent_forms();
        return consent_forms.stream().map(TransferModelsDTO::MappingConent_View).collect(Collectors.toList());
    }

    @Override
    public Consent_formsDTO addConsent_forms(Consent_formsDTO dto) {
        // 1. Kiểm tra tồn tại các entity liên kết
        Student student = studentRepo.getStudent(dto.getStudentId());
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        Vaccine_Batches vaccineBatch = vaccinesRepo.GetVaccineByVaccineId(dto.getVaccineBatchId());
        if (vaccineBatch == null) {
            throw new RuntimeException("Vaccine batch not found");
        }

        Parent parent = parentRepo.GetParentById(dto.getParentID());
        if (parent == null) {
            throw new RuntimeException("Parent not found");
        }

        // 2. Tạo entity mới từ DTO
        Consent_forms consent = new Consent_forms();

        // Đảm bảo đây là tạo mới
        consent.setConsent_id(null);

        // Gán các quan hệ (student, parent, vaccine) đã tồn tại
        consent.setStudent(student);
        consent.setParent(parent);
        consent.setVaccineBatches(vaccineBatch);

        // Gán thông tin còn lại từ DTO
        consent.setSend_date(dto.getSend_date());
        consent.setExpire_date(dto.getExpire_date());

        consent.setHasAllergy(dto.getHasAllergy());
        consent.setReason(dto.getReason());
        consent.setStatus(dto.getStatus());
        consent.setIsAgree(dto.getIsAgree());

        // 3. Lưu vào database
        Consent_forms saved = consent_formsRepo.addConsent_forms(consent);

        // 4. Trả lại DTO
        return TransferModelsDTO.MappingConsent(saved);
    }


    @Override
    public List<Consent_formsDTO> getConsent_formsByParentName(String fullName) {
        var consent_formsName = consent_formsRepo.getConsent_formsByParentName(fullName);
        return consent_formsName.stream().map(TransferModelsDTO::MappingConsent).collect(Collectors.toList());
    }

    @Override
    public Consent_formViewDTO getConsentFormForParent(Integer consentFormId) {
        Consent_forms entity = consent_formsRepo.getConsent_formsById(consentFormId);
        if (entity == null) {
            throw new RuntimeException("Consent Form not found");
        }
        return TransferModelsDTO.MappingConent_View(entity);
    }

    @Override
    public List<Consent_formsDTO> getConsent_formsIsAgree(String dot) {
        var listArrgee = consent_formsRepo.getConsent_formsIsAgree(dot);

        if (listArrgee.isEmpty()) {
            throw new RuntimeException("List of consent_forms is empty");
        }
        List<Consent_formsDTO> dtoList = listArrgee.stream()
                .map(TransferModelsDTO::MappingConsent)
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public List<Consent_formsDTO> getConsent_formsClass(String class_name) {
        var className = consent_formsRepo.getConsent_formsClass(class_name);
        if (className == null) throw new RuntimeException("Class not found");
        return className.stream().map(TransferModelsDTO::MappingConsent).collect(Collectors.toList());
    }

    @Override
    public void parentConfirm(ParentConfirmDTO dto) {
        Consent_forms entity = consent_formsRepo.getConsent_formsById(dto.getConsentFormId());
        if (entity == null) {
            throw new RuntimeException("Consent Form not found");
        }
        entity.setIsAgree(dto.getIsAgree());
        entity.setReason(dto.getReason());
        entity.setHasAllergy(dto.getHasAllergy());
        consent_formsRepo.updateConsent_forms(entity);
    }

    @Override
    public Long countConsentFormsIsAgreeByBatch(String dot) {
        return consent_formsRepo.countConsentFormsIsAgreeByBatch(dot);
    }

    @Override
    public Long countConsentFormsDisAgreeByBatch(String dot)  {
        return consent_formsRepo.countConsentFormsDisAgreeByBatch(dot);
    }


    @Override
    public Long countConsentFormsPendingByBatch(String dot) {
        return consent_formsRepo.countConsentFormsPendingByBatch(dot);
    }

    @Override
    public List<Consent_formViewDTO>  getConsentByStudentId(int studentId) {
        var  list = consent_formsRepo.getConsentByStudentId(studentId);
        return list.stream().map(TransferModelsDTO::MappingConent_View).collect(Collectors.toList());
    }

    @Override
    public List<Consent_formViewDTO> findPendingForParent(int parentId) {
        var consent_forms = consent_formsRepo.findPendingForParent(parentId);
        return consent_forms.stream().map(TransferModelsDTO::MappingConent_View).collect(Collectors.toList());
    }

    @Override
    public void processParentResponse(ConsentFormParentResponseDTO dto) {
        // 1. Tìm phiếu đồng ý theo ID
        Consent_forms form = consent_formsRepos.findById(dto.getConsentFormId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy phiếu với ID: " + dto.getConsentFormId()));

        // 2. Nếu phụ huynh từ chối nhưng không ghi lý do thì báo lỗi
        if ("Không đồng ý".equalsIgnoreCase(dto.getIsAgree())) {
            if (dto.getReason() == null || dto.getReason().trim().isEmpty()) {
                throw new IllegalArgumentException("Vui lòng ghi rõ lý do từ chối.");
            }
        }

        // 3. Cập nhật phiếu
        form.setIsAgree(dto.getIsAgree());
        form.setReason(dto.getReason());
        form.setHasAllergy(dto.getHasAllergy());
        form.setStatus("ĐÃ PHÊ DUYỆT");

        // 👉 Nếu bạn để transactional bên ngoài, hãy đảm bảo catch lỗi đúng để không rollback
        consent_formsRepos.save(form);

        // 4. Nếu phụ huynh đồng ý → tạo hồ sơ tiêm chủng và gửi email
        if ("Đồng ý".equalsIgnoreCase(dto.getIsAgree())) {
            try {
                // Kiểm tra xem có đủ dữ liệu để tạo record chưa
                Student student = form.getStudent();
                Vaccine_Batches batch = form.getVaccineBatches();

                if (student == null || batch == null) {
                    throw new IllegalStateException("Thiếu thông tin học sinh hoặc lô vaccine.");
                }

                Vaccination_records_SentParent_DTO recordDTO = new Vaccination_records_SentParent_DTO();
                recordDTO.setCosentID(form.getConsent_id());
                recordDTO.setStudentId(student.getStudentID());
                recordDTO.setVaccineBatchId(batch.getBatchID());
//                recordDTO.setCreateNurseName(form.getVaccineBatches().getCreatedByNurse().getFullName());
//                recordDTO.setCreateNurseID(form.getVaccineBatches().getCreatedByNurse().getNurseID());
//                recordDTO.setEditnurseID(form.getVaccineBatches().getUpdatedByNurse().getNurseID());
//                recordDTO.setEditNurseName(form.getVaccineBatches().getUpdatedByNurse().getFullName());

                // Gọi tạo form và gửi email
                vaccinationRecordsService.createEmail(recordDTO);

            } catch (Exception ex) {
                // 👉 Ghi log để kiểm tra mà không rollback toàn bộ transaction
                System.err.println("Lỗi khi tạo hồ sơ tiêm chủng và gửi email: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }



    @Override
    public List<Consent_formViewDTO> getAllConsentForms() {
       var listConsent = consent_formsRepo.getAllConsentForms();
        return listConsent.stream().map(TransferModelsDTO::MappingConent_View).collect(Collectors.toList());
    }

    @Override
    public Consent_formsDTO updateConsent(Consent_formsDTO consentFormsDTO) {
       var updateConsent = consent_formsRepo.updateConsent(TransferModelsDTO.MappingConsentDTO(consentFormsDTO));
       return TransferModelsDTO.MappingConsent(updateConsent);
    }

    @Override
    public SendConsentFormResult sendConsentFormsByClassName(
            List<String> className,
            Integer batchId,
            LocalDateTime sendDate,
            LocalDateTime expireDate,
            String status
    ) {

        Vaccine_Batches batch = vaccineBatchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lô vắc xin"));

        List<String> getALlClass = studentRepository.findAllClassNames();
        List<Consent_forms> sentForms = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (String nameclass : className) {
            if (!getALlClass.contains(nameclass)) {
                errors.add("Không tìm thấy lớp: " + nameclass);
                continue;
            }

            List<Student> students = studentRepository.findByClassName(nameclass);

            for (Student student : students) {
                Parent parent = student.getParent();
                if (parent == null) {
                    errors.add("Không tìm thấy phụ huynh cho học sinh: " + student.getFullName());
                    continue;
                }

                // Tạo và lưu form
                Consent_forms form = new Consent_forms();
                form.setStudent(student);
                form.setParent(parent);
                form.setVaccineBatches(batch);
                form.setSend_date(sendDate);
                form.setExpire_date(expireDate);
                form.setStatus(status);
                consent_formsRepos.save(form);
                sentForms.add(form);

                // Gửi thông báo cho từng phụ huynh
                String title = "Gửi phiếu tiêm chủng cho học sinh " + student.getFullName();
                String content = "Một đợt tiêm chủng mới đã được lên lịch cho học sinh " + student.getFullName() +
                        ". Vui lòng truy cập hệ thống để xác nhận đồng ý hoặc từ chối.";

                NotificationsParent notification = new NotificationsParent();
                notification.setParent(parent);
                notification.setTitle(title);
                notification.setContent(content);
                notification.setCreateAt(LocalDateTime.now());
                notification.setStatus(false); // chưa đọc

                Integer notificationId = notificationsParentService.createAutoNotification(
                        parent.getParentID(), title, content);
                emailService.sendHtmlNotificationEmail(parent, title, content, notificationId);
            }
        }
        return new SendConsentFormResult(sentForms, errors);
    }


    @Override
    public List<Consent_form_dot> findDot() {
        return consent_formsRepo.findDot();
    }

    @Override
    public List<Consent_formsDTO> getIsAgree() {
        var list = consent_formsRepo.getIsAgree();
        return list.stream().map(TransferModelsDTO::MappingConsent).collect(Collectors.toList());
    }

    @Override
    public List<Consent_formsDTO> getDisAgree() {
        var list = consent_formsRepo.getDisAgree();
        return list.stream().map(TransferModelsDTO::MappingConsent).collect(Collectors.toList());
    }

    @Override
    public List<Consent_formsDTO> getStatus() {
        var list = consent_formsRepo.getStatus();
        return list.stream().map(TransferModelsDTO::MappingConsent).collect(Collectors.toList());
    }

    @Override
    public List<Consent_formsDTO> getDisStatus() {
        var list = consent_formsRepo.getDisStatus();
        return list.stream().map(TransferModelsDTO::MappingConsent).collect(Collectors.toList());
    }
}
