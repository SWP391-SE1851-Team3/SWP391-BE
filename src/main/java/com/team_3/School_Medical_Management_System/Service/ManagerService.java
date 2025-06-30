package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.ManagerDTO;
import com.team_3.School_Medical_Management_System.DTO.ParentDTO;
import com.team_3.School_Medical_Management_System.DTO.ParentManagerDTO;
import com.team_3.School_Medical_Management_System.DTO.UserDTO;
import com.team_3.School_Medical_Management_System.InterFaceSerivceInterFace.ManagerServiceInterFace;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import com.team_3.School_Medical_Management_System.Repositories.ParentRepo;
import com.team_3.School_Medical_Management_System.Repositories.Post_vaccination_observationsRepo;
import com.team_3.School_Medical_Management_System.Repositories.SchoolNurseRepo;
import com.team_3.School_Medical_Management_System.Repositories.StudentRepo;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ManagerService implements ManagerServiceInterFace {
    private ManagerInterFace managerInterFace;
    @Autowired
    private HealthCheckStudentRepository healthCheckStudentRepo;
    @Autowired
    private Consent_formsInterFace consentFormsRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private Vaccination_recordsInterFace vaccinationRecordRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private Post_vaccination_observationsInterFace postVaccinationObservationRepo;
    @Autowired
    private SchoolNurseRepo nurseRepository;

    @Autowired
    private MedicalEventDetailsRepository medicalEventDetailsRepo;
    @Autowired
    private MedicationSubmissionInterFace medicationSubmissionRepo;
    @Autowired
    private ParentRepo parentRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private StudentHealthProfileInterFace studentHealthProfileRepo;
    @Autowired
    private MedicationDetailRepository medicationDetailRepo;
    @Autowired
    private HealthConsentFormRepoInterface healthConsentFormRepo;
    @Autowired
    private HealthConsultationRepository healthConsultationRepo;
    @Autowired
    private ConfirmMedicationSubmissionInterFace confirmMedicationSubmissionRepo;
    @Autowired
    private MedicalEventRepo medicalEventRepo;
    @Autowired
    private MedicalEvent_EventTypeRepo medicalEventEventTypeRepo;
    @Autowired
    private MedicalEvent_NurseRepo medicalEventNurseRepo;
    @Autowired
    private NotificationsMedicalEventDetailsRepository notificationsMedicalEventDetailsRepo;
    @Autowired
    private NotificationsParentRepository notificationsParentRepo;
    @Autowired
    private HealthConsultationParentRepo healthConsultationParentRepo;


    @Autowired
    public ManagerService(ManagerInterFace managerInterFace) {
        this.managerInterFace = managerInterFace;
    }

    @Override
    public Manager LoginByAccount(String Email, String Password) {
        var p = managerInterFace.LoginByAccount(Email, Password);
        if (p.getEmail().length() < 6) {
            throw new RuntimeException("Email Manager too short");
        } else if (p.getPassword().isEmpty()) {
            throw new RuntimeException("Password Manager is empty");
        } else {
            return p;
        }
    }

    @Override
    public ManagerDTO createManager(ManagerDTO managerDTO) {
        Manager manager = new Manager();
        Role role = roleRepo.findById(managerDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + managerDTO.getRoleId()));
        manager.setUserName(managerDTO.getUsername());
        manager.setPassword(managerDTO.getPassword());
        manager.setFullName(managerDTO.getFullName());
        manager.setPhone(managerDTO.getPhone());
        manager.setEmail(managerDTO.getEmail());
        manager.setIsActive(managerDTO.getIsActive());
        manager.setRole(role);

        Manager savedManager = managerRepository.save(manager);
        managerDTO.setManagerId(savedManager.getManagerID());
        return managerDTO;

    }

    @Override
    public ManagerDTO updateManager(int id, ManagerDTO managerDTO) {
        Optional<Manager> managerOptional = managerRepository.findById(id);
        Role role = roleRepo.findById(managerDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + managerDTO.getRoleId()));
        if (managerOptional.isPresent()) {
            Manager manager = managerOptional.get();
            manager.setUserName(managerDTO.getUsername());
            if (managerDTO.getPassword() != null && !managerDTO.getPassword().isEmpty()) {
                manager.setPassword(managerDTO.getPassword());
            }
            manager.setFullName(managerDTO.getFullName());
            manager.setPhone(managerDTO.getPhone());
            manager.setEmail(managerDTO.getEmail());
            manager.setIsActive(managerDTO.getIsActive());
            manager.setRole(role);

            Manager updatedManager = managerRepository.save(manager);
            managerDTO.setManagerId(updatedManager.getManagerID());
            return managerDTO;
        }
        return null;

    }

    @Override
    public boolean deleteManager(int id) {
        if (managerRepository.existsById(id)) {
            managerRepository.deleteById(id);
            return true;
        }
        return false;

    }


    @Override
    public ResponseEntity<?> createUser(UserDTO userDTO) {

        Optional<Role> role = roleRepo.findById(userDTO.getRoleId());
        if (!role.isPresent()) {
            return ResponseEntity.badRequest().body("RoleID không tồn tại");
        }

        // Kiểm tra Username
        if (parentRepository.existsByUserName(userDTO.getUserName()) ||
                nurseRepository.existsByUserName(userDTO.getUserName())) {
            return ResponseEntity.badRequest().body("Username đã tồn tại");
        }

        // Kiểm tra Email
        if (parentRepository.existsByEmail(userDTO.getEmail()) ||
                nurseRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email đã tồn tại");
        }


        switch (userDTO.getRoleId()) {

            case 1: // Parent
                Parent p = new Parent();

                p.setUserName(userDTO.getUserName());
                p.setPassword(userDTO.getPassword());
                p.setFullName(userDTO.getFullName());
                p.setPhone(userDTO.getPhone());
                p.setEmail(userDTO.getEmail());
                p.setIsActive(userDTO.getIsActive());
                p.setOccupation(userDTO.getOccupation());
                p.setRelationship(userDTO.getRelationship());
                p.setRoleID(userDTO.getRoleId());

                parentRepository.AddNewParent(p);
                return ResponseEntity.ok(p);


            case 2: // SchoolNurse
                SchoolNurse n = new SchoolNurse();
                n.setUserName(userDTO.getUserName());
                n.setPassword(userDTO.getPassword());
                n.setFullName(userDTO.getFullName());
                n.setPhone(userDTO.getPhone());
                n.setEmail(userDTO.getEmail());
                n.setIsActive(userDTO.getIsActive());
                n.setCertification(userDTO.getCertification());
                n.setSpecialisation(userDTO.getSpecialisation());
                n.setRoleID(userDTO.getRoleId());

                nurseRepository.AddNewSchoolNurses(n);
                return ResponseEntity.ok(n);

            default:
                return ResponseEntity.badRequest().body("Không thể thêm mới tài khoản cho vai trò này!");
        }


    }

    @Override
    public ResponseEntity<?> updateUser(int id, int roleId, UserDTO userDTO) {

        switch (roleId) {
            case 1:
                Parent parent = parentRepository.checkIdAndRoleExist(id, roleId);
                if (parent == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parent with ID " + id + " and RoleID " + roleId + " not found.");
                } else {
                    Parent p = parentRepository.GetParentById(id);
                    p.setUserName(userDTO.getUserName());
                    //    p.setUserName(userDTO.getUserName());
                    p.setPassword(userDTO.getPassword());
                    p.setFullName(userDTO.getFullName());
                    p.setPhone(userDTO.getPhone());
                    p.setEmail(userDTO.getEmail());
                    p.setIsActive(userDTO.getIsActive());
                    p.setOccupation(userDTO.getOccupation());
                    p.setRelationship(userDTO.getRelationship());
                    p.setRoleID(p.getRoleID());
                    Parent updateP = parentRepository.UpdateParent(p);
                    return ResponseEntity.ok(updateP);
                }
            case 2:

                SchoolNurse n = nurseRepository.checkIdAndRoleExist(id, roleId);

                if (n == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("SchoolNurse with ID " + id + " and RoleID " + roleId + " not found.");
                }
                //SchoolNurse n = nurseRepository.GetSchoolNursesById(id);
                n.setUserName(userDTO.getUserName());
                n.setPassword(userDTO.getPassword());
                n.setFullName(userDTO.getFullName());
                n.setPhone(userDTO.getPhone());
                n.setEmail(userDTO.getEmail());
                n.setIsActive(userDTO.getIsActive());
                n.setCertification(userDTO.getCertification());
                n.setSpecialisation(userDTO.getSpecialisation());
                n.setRoleID(n.getRoleID());
                SchoolNurse updateN = nurseRepository.UpdateSchoolNurses(n);
                return ResponseEntity.ok(updateN);
        }
        return null;
    }






    @Transactional(rollbackOn = Exception.class)
    @Override
    public ResponseEntity<String> deleteUser(int id, int roleId) {


        switch (roleId) {
            case 1: // Parent
                Parent parent = parentRepository.checkIdAndRoleExist(id, roleId);
                if (parent == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Parent with ID " + id + " and RoleID " + roleId + " not found.");
                } else {
                    try {
                        // 1. Lấy danh sách students của parent này
                        List<Student> students = studentRepo.getStudentsByParentID(parent.getParentID());

                        // 2. Xóa tất cả dữ liệu liên quan đến từng student
                        for (Student student : students) {
                            deleteStudentRelatedData(student.getStudentID());
                        }

                        // 3. Xóa dữ liệu liên quan trực tiếp đến Parent
                        deleteParentRelatedData(parent.getParentID());

                        // 4. Xóa students
                        for (Student student : students) {
                            studentRepo.removeStudent(student.getStudentID());
                        }

                        // 5. Cuối cùng xóa Parent
                        parentRepository.DeleteParent(id);

                        return ResponseEntity.ok("Parent and all related data deleted successfully.");

                    } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error deleting parent: " + e.getMessage());
                    }

                }

            case 2: // SchoolNurse

                SchoolNurse nurse = nurseRepository.checkIdAndRoleExist(id, roleId);
                if (nurse == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("SchoolNurse with ID " + id + " and RoleID " + roleId + " not found.");
                }


                // Xóa tất cả dữ liệu liên quan đến SchoolNurse

                nurseRepository.DeleteSchoolNurses(id);
                break;

            default:
                return ResponseEntity.badRequest().body("RoleID không hợp lệ");
        }

        return ResponseEntity.notFound().build();


    }

    private void deleteStudentRelatedData(int studentId) {
        // Xóa vaccination records và post-vaccination observations
        List<Vaccination_records> vaccinationRecords = vaccinationRecordRepo.getVaccination_recordsByStudentId(studentId);
        for (Vaccination_records record : vaccinationRecords) {
            postVaccinationObservationRepo.deleteByVaccinationRecordId(record.getVaccinationRecordID());
        }
        vaccinationRecordRepo.deleteByStudentId(studentId);

        // Xóa health check student
        healthCheckStudentRepo.deleteByStudentID(studentId);

        // Xóa consent forms
        consentFormsRepo.deleteConsentFormsByStudentId(studentId);

        // Xóa health consent form
        healthConsentFormRepo.deleteByStudentId(studentId);

        // Xóa student health profile
        studentHealthProfileRepo.deleteHealthProfile(studentId);

        // Xóa medical event details
        medicalEventDetailsRepo.deleteByStudent_StudentID(studentId);

        // Xóa health consultation liên quan
        healthConsultationRepo.deleteByStudent_StudentID(studentId);
    }



    private void deleteParentRelatedData(int parentId) {
        // Xóa medication submissions và details
        List<MedicationSubmission> submissions = medicationSubmissionRepo.findByParentId(parentId);
        for (MedicationSubmission submission : submissions) {
            medicationDetailRepo.deleteByMedicationSubmission_MedicationSubmissionId(submission.getMedicationSubmissionId());
            confirmMedicationSubmissionRepo.deleteByMedicationSubmissionId(submission.getMedicationSubmissionId());
        }
        medicationSubmissionRepo.deleteByParentIdm(parentId);

        // Xóa medical events của parent này
        List<MedicalEvent> events = medicalEventRepo.getByParentId(parentId);
        for (MedicalEvent event : events) {
            medicalEventEventTypeRepo.deleteByMedicalEvent_EventID(event.getEventID());
            medicalEventNurseRepo.deleteByMedicalEvent_EventID(event.getEventID());
            notificationsMedicalEventDetailsRepo.deleteByMedicalEvent_EventID(event.getEventID());
        }
        medicalEventRepo.deleteByParentID(parentId);

        // Xóa notifications
        notificationsParentRepo.deleteByParentId(parentId);

        // Xóa health consultation parent
        healthConsultationParentRepo.deleteByParentID(parentId);

        // Xóa consent forms của parent
        consentFormsRepo.deleteByConsent_FormByParentID(parentId);

        // Xóa health consent forms của parent
        healthConsentFormRepo.deleteByParentID(parentId);
    }

}





































