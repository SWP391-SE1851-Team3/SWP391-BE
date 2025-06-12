package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.NotificationsParentDTO;
import com.team_3.School_Medical_Management_System.Service.NotificationsParentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsParentController {
    @Autowired
    private NotificationsParentService notificationsParentService; // Service cho NotificationsParent

    @PostMapping("/medical-event/{parentId}/{eventId}")
    @Operation(summary = "Gửi thông báo cho phụ huynh về sự kiện y tế")

    public ResponseEntity<NotificationsParentDTO> sendNotificationForMedicalEvent(
            @PathVariable Integer parentId,
            @PathVariable Integer eventId,
            @PathVariable String content,
            @PathVariable boolean status) {
        NotificationsParentDTO result = notificationsParentService.sendNotification(parentId, eventId, content, status);
        return ResponseEntity.status(201).body(result);
    }
}
