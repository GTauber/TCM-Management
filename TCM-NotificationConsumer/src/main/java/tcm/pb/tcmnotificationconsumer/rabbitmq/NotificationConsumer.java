package tcm.pb.tcmnotificationconsumer.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tcm.pb.tcmnotificationconsumer.model.Notification;
import tcm.pb.tcmnotificationconsumer.service.NotificationService;

@RequiredArgsConstructor
@Component
@Slf4j
public class NotificationConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;

    @RabbitListener(queues = {"notification-queue"})
    public void receive(@Payload String json) {
        try {
            var notification = objectMapper.readValue(json, Notification.class);
            log.info("Notification received: {}", notification);
            notificationService.process(notification);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
