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
                                MedicalEventStatsRepository medicalEventStatsRepository,
                                VaccinationStatsRepository vaccinationStatsRepository,
                                HealthCheckStatsRepository healthCheckStatsRepository,
                                MedicationStatsRepository medicationStatsRepository) {

        this.systemStatsRepository = systemStatsRepository;
        this.medicalEventStatsRepository = medicalEventStatsRepository;
        this.vaccinationStatsRepository = vaccinationStatsRepository;
        this.healthCheckStatsRepository = healthCheckStatsRepository;
        this.medicationStatsRepository = medicationStatsRepository;
    }



    @Override
    public DashboardReportModel getFullReport() {


        return DashboardReportModel.builder()
                .systemStats(getSystemStats())
                .medicalEventStats(getMedicalEventStats())
                .vaccinationStats(getVaccinationStats())
                .healthCheckStats(getHealthCheckStats())
                .medicationStats(getMedicationStats())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public SystemStats getSystemStats() {


        return SystemStats.builder()
                .totalStudents(systemStatsRepository.countStudents())
                .activeStudents(systemStatsRepository.countActiveStudents())
                .totalParents(systemStatsRepository.countParents())
                .totalNurses(systemStatsRepository.countNurses())
                .totalManagers(systemStatsRepository.countManagers())
                .build();
    }

    @Override
    public MedicalEventStats getMedicalEventStats() {


        return MedicalEventStats.builder()
                .totalEvents(medicalEventStatsRepository.countTotalEvents())
                .emergencyEvents(medicalEventStatsRepository.countEmergencyEvents())
                .completedEvents(medicalEventStatsRepository.countCompletedEvents())
                .pendingEvents(medicalEventStatsRepository.countPendingEvents())
                .notificationRate(medicalEventStatsRepository.calculateNotificationRate())
                .build();
    }

    @Override
    public VaccinationStats getVaccinationStats() {


        return VaccinationStats.builder()
                .totalBatches(vaccinationStatsRepository.countTotalBatches())
                .completedBatches(vaccinationStatsRepository.countCompletedBatches())
                .totalVaccinated(vaccinationStatsRepository.countTotalVaccinated())
                .consentRate(vaccinationStatsRepository.calculateConsentRate())
                .totalReactions(vaccinationStatsRepository.countTotalReactions())
                .build();
    }

    @Override
    public HealthCheckStats getHealthCheckStats() {


        return HealthCheckStats.builder()
                .totalSchedules(healthCheckStatsRepository.countTotalSchedules())
                .completedSchedules(healthCheckStatsRepository.countCompletedSchedules())
                .totalChecked(healthCheckStatsRepository.countTotalChecked())
                .consentRate(healthCheckStatsRepository.calculateConsentRate())
                .averageBMI(healthCheckStatsRepository.calculateAverageBMI())
                .build();
    }

    @Override
    public MedicationStats getMedicationStats() {


        return MedicationStats.builder()
                .totalSubmissions(medicationStatsRepository.countTotalSubmissions())
                .approvedSubmissions(medicationStatsRepository.countApprovedSubmissions())
                .rejectedSubmissions(medicationStatsRepository.countRejectedSubmissions())
                .approvalRate(medicationStatsRepository.calculateApprovalRate())
                .build();
    }
}
