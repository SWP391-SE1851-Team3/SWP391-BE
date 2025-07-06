package com.team_3.School_Medical_Management_System.DTO;

import com.team_3.School_Medical_Management_System.Model.Consent_forms;
import lombok.*;

import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendConsentFormResult {
    private List<Consent_forms> sentForms;
    private List<String> errors;

}
