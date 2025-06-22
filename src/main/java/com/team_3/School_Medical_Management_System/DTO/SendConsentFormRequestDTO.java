package com.team_3.School_Medical_Management_System.DTO;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendConsentFormRequestDTO {
    private String className;
    private Integer batchId;
    private LocalDateTime sendDate;
    private LocalDateTime expireDate;
    private String status;
}
