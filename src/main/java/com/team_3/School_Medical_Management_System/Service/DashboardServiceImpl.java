package com.team_3.School_Medical_Management_System.Service;

import com.team_3.School_Medical_Management_System.InterfaceRepo.*;
import com.team_3.School_Medical_Management_System.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class DashboardServiceImpl implements DashboardService{



    private  SystemStatsRepository systemStatsRepository;
    private  MedicalEventStatsRepository medicalEventStatsRepository;
    private  VaccinationStatsRepository vaccinationStatsRepository;
    private  HealthCheckStatsRepository healthCheckStatsRepository;
    private  MedicationStatsRepository medicationStatsRepository;

    @Autowired
    public DashboardServiceImpl(MedicalSupplyRepository medicalSupplyRepository,
                                SystemStatsRepository systemStatsRepository,
                                MedicalEventStatsRepository medicalEventStatsReposit,
                                VaccinationStatsRepository vaccinationStatsRepository,
                                HealthCheckStatsRepository healthCheckStatsRepository,
                                MedicationStatsRepository medicationStatsRepository) {

        this.systemStatsRepository = systemStatsRepository;
        this.medicalEventStatsRepository = medicalEventStatsReposit;
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
}
