package tcm.pb.tcmnotification.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import tcm.pb.tcmnotification.model.Notification;

@Service
@RequiredArgsConstructor
public class NotificationProducer {

    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    public void send(Notification notification) throws JsonProcessingException {
        amqpTemplate.convertAndSend("notification-exc", "notification-rk", objectMapper.writeValueAsString(notification));
    }

}
