package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.DTO.AccountDTO;
import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

private SchoolNurseRepository schoolNurseRepository;
    private ParentRepository parentRepository;
    private SystemStatsRepository systemStatsRepository;
    private MedicalEventStatsRepository medicalEventStatsRepository;
    private VaccinationStatsRepository vaccinationStatsRepository;
    private HealthCheckStatsRepository healthCheckStatsRepository;
    private MedicationStatsRepository medicationStatsRepository;

    @Autowired
    public DashboardServiceImpl(SchoolNurseRepository schoolNurseRepository, ParentRepository parentRepository,
                                SystemStatsRepository systemStatsRepository,
                                MedicalEventStatsRepository medicalEventStatsRepository,
                                VaccinationStatsRepository vaccinationStatsRepository,
                                HealthCheckStatsRepository healthCheckStatsRepository,
                                MedicationStatsRepository medicationStatsRepository) {
        this.schoolNurseRepository = schoolNurseRepository;
        this.parentRepository = parentRepository;
        this.systemStatsRepository = systemStatsRepository;
        this.medicalEventStatsRepository = medicalEventStatsRepository;
        this.vaccinationStatsRepository = vaccinationStatsRepository;
        this.healthCheckStatsRepository = healthCheckStatsRepository;
        this.medicationStatsRepository = medicationStatsRepository;
    }


    @Override
    public DashboardReportModel getFullReport(LocalDateTime startDate, LocalDateTime endDate) {


        return DashboardReportModel.builder()
                .systemStats(getSystemStats())
                .medicalEventStats(getMedicalEventStats(startDate, endDate))
                .vaccinationStats(getVaccinationStats(startDate, endDate))
                .healthCheckStats(getHealthCheckStats(startDate, endDate))
                .medicationStats(getMedicationStats(startDate, endDate))

                .build();
    }

    @Override
    public SystemStats getSystemStats() {


        return SystemStats.builder()
                .totalStudents(systemStatsRepository.countStudents())
                .activeStudents(systemStatsRepository.countActiveStudents())
                .totalParents(systemStatsRepository.countParents())
                .totalNurses(systemStatsRepository.countNurses())
                .totalManagers(systemStatsRepository.countManagers()).totalMedicalSupplies(systemStatsRepository.countMedicalSupplies())
                .build();
    }

    @Override
    public MedicalEventStats getMedicalEventStats(LocalDateTime startDate, LocalDateTime endDate) {


        return MedicalEventStats.builder()
                .totalEvents(medicalEventStatsRepository.countTotalEvents(startDate, endDate))
                .emergencyEvents(medicalEventStatsRepository.countEmergencyEvents(startDate, endDate))
                .completedEvents(medicalEventStatsRepository.countCompletedEvents(startDate, endDate))
                .pendingEvents(medicalEventStatsRepository.countPendingEvents(startDate, endDate))
                .notificationRate(medicalEventStatsRepository.calculateNotificationRate(startDate, endDate))
                //  .createdDay(medicalEventStatsRepository.getCreatedDay())
                .build();
    }

    @Override
    public VaccinationStats getVaccinationStats(LocalDateTime startDate, LocalDateTime endDate) {


        return VaccinationStats.builder()
                .totalBatches(vaccinationStatsRepository.countTotalBatches(startDate, endDate))
                .completedBatches(vaccinationStatsRepository.countCompletedBatches(startDate, endDate))
                .totalVaccinated(vaccinationStatsRepository.countTotalVaccinated(startDate, endDate))
                .consentRate(vaccinationStatsRepository.calculateConsentRate(startDate, endDate))
                .totalReactions(vaccinationStatsRepository.countTotalReactions(startDate, endDate))
                .build();
    }

    @Override
    public HealthCheckStats getHealthCheckStats(LocalDateTime startDate, LocalDateTime endDate) {


        return HealthCheckStats.builder()
                .totalSchedules(healthCheckStatsRepository.countTotalSchedules(startDate, endDate))
                .completedSchedules(healthCheckStatsRepository.countCompletedSchedules(startDate, endDate))
                .totalChecked(healthCheckStatsRepository.countTotalChecked(startDate, endDate))
                .consentRate(healthCheckStatsRepository.calculateConsentRate(startDate, endDate))
                .averageBMI(healthCheckStatsRepository.calculateAverageBMI(startDate, endDate))
                .build();
    }

    @Override
    public MedicationStats getMedicationStats(LocalDateTime startDate, LocalDateTime endDate) {


        return MedicationStats.builder()
                .totalSubmissions(medicationStatsRepository.countTotalSubmissions(startDate, endDate))
                .approvedSubmissions(medicationStatsRepository.countApprovedSubmissions(startDate, endDate))
                .rejectedSubmissions(medicationStatsRepository.countRejectedSubmissions(startDate, endDate))
                .approvalRate(medicationStatsRepository.calculateApprovalRate(startDate, endDate))
                .build();
    }

    @Override
    public List<AccountDTO> getAllAccounts(int roleID) {
        if (roleID == 1) {

            List<Parent> parents = parentRepository.getAllBy();
            List<AccountDTO> a = new ArrayList<>();
            for (Parent p : parents) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setUsername(p.getUserName());
                accountDTO.setPassword(p.getPassword());
                accountDTO.setEmail(p.getEmail());
                accountDTO.setPhone(p.getPhone());
                accountDTO.setFullName(p.getFullName());
                // Add to a list or return as needed
                a.add(accountDTO);
            }
            return a;

        } else if (roleID == 2) {
            List<SchoolNurse> nurses = schoolNurseRepository.getAll();
            List<AccountDTO> a = new ArrayList<>();
            for (SchoolNurse n : nurses) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setUsername(n.getUserName());
                accountDTO.setPassword(n.getPassword());
                accountDTO.setEmail(n.getEmail());
                accountDTO.setPhone(n.getPhone());
                accountDTO.setFullName(n.getFullName());
                // Add to a list or return as needed
                a.add(accountDTO);
            }
            return a;

        } else {
            // Handle other roles or return an empty list
            return null;

        }

        }
    }
