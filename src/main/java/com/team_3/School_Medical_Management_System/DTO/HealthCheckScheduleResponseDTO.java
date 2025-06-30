package com.team_3.School_Medical_Management_System.DTO;

import java.util.Date;

public class HealthCheckScheduleResponseDTO {
    private Integer health_ScheduleID;
    private Date schedule_Date;
    private String name;
    private String location;
    private String notes;
    private String status;
    private Date create_at;
    private Date update_at;
    private String updatedByNurseName;
    private Integer updatedByNurseID;

    // Getters and setters
    public Integer getHealth_ScheduleID() {
        return health_ScheduleID;
    }
    public void setHealth_ScheduleID(Integer health_ScheduleID) {
        this.health_ScheduleID = health_ScheduleID;
    }
    public Date getSchedule_Date() {
        return schedule_Date;
    }
    public void setSchedule_Date(Date schedule_Date) {
        this.schedule_Date = schedule_Date;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getCreate_at() {
        return create_at;
    }
    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
    }
    public Date getUpdate_at() {
        return update_at;
    }
    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }
    public String getUpdatedByNurseName() {
        return updatedByNurseName;
    }
    public void setUpdatedByNurseName(String updatedByNurseName) {
        this.updatedByNurseName = updatedByNurseName;
    }
    public Integer getUpdatedByNurseID() {
        return updatedByNurseID;
    }
    public void setUpdatedByNurseID(Integer updatedByNurseID) {
        this.updatedByNurseID = updatedByNurseID;
    }
}

