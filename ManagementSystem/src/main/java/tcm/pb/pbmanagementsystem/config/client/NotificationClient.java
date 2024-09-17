package tcm.pb.pbmanagementsystem.config.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import tcm.pb.pbmanagementsystem.model.Notification;

@FeignClient("TCM-NOTIFICATION")
public interface NotificationClient {

    @PostMapping("/v1/notification")
    void sendNotification(Notification notification);

}
