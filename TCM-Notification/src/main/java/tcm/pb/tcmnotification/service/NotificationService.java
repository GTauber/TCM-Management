package tcm.pb.tcmnotification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tcm.pb.tcmnotification.model.Notification;
import tcm.pb.tcmnotification.rabbitmq.NotificationProducer;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationProducer producer;

    public void sendNotification(Notification notification) throws JsonProcessingException {
        log.info("Sending notification: {}", notification.toString());
        producer.send(notification);
    }

}
