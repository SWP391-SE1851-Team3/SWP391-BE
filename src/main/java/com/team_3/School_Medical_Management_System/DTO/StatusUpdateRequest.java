package com.team_3.School_Medical_Management_System.DTO;

public class StatusUpdateRequest {
    private String status;
    private String reason;
    private Integer nurseId;
    private String evidence;
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public Integer getNurseId() {
        return nurseId;
    }
    public void setNurseId(Integer nurseId) {
        this.nurseId = nurseId;
    }
    public String getEvidence() {
        return evidence;
    }
    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }
}

