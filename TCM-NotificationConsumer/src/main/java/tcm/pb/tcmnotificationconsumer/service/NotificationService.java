package tcm.pb.tcmnotificationconsumer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tcm.pb.tcmnotificationconsumer.model.Notification;

@Service
@Slf4j
public class NotificationService {

    public void process(Notification notification) {
        log.info("Processing notification: {}", notification);
    }

}
