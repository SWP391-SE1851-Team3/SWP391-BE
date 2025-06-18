package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.NotificationsParentDTO;
import com.team_3.School_Medical_Management_System.Service.NotificationsParentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsParentController {
    @Autowired
    private NotificationsParentService notificationsParentService; // Service cho NotificationsParent

    @PostMapping
    @Operation(summary = "Gửi thông báo cho phụ huynh")
    public ResponseEntity<NotificationsParentDTO> sendNotification(@RequestBody NotificationsParentDTO dto) {
        NotificationsParentDTO result = notificationsParentService.sendNotification(dto);
        return ResponseEntity.ok(result);
    }
}
