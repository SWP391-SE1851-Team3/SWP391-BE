package com.team_3.School_Medical_Management_System.DTO;

public class StatusUpdateDTO {
    private String status;

    public StatusUpdateDTO(String status) {
        this.status = status;
    }

    public StatusUpdateDTO() {
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StatusUpdateDTO{" +
                "status='" + status + '\'' +
                '}';
    }
}
