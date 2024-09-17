package tcm.pb.tcmnotification.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcm.pb.tcmnotification.model.Notification;
import tcm.pb.tcmnotification.service.NotificationService;

@RestController
@RequestMapping("/v1/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Map<String, String>> sendNotification(@RequestBody Notification notification)
        throws JsonProcessingException {
        notificationService.sendNotification(notification);
        return ResponseEntity.ok(Map.of("message:", "Notification sent successfully!"));
    }

}
