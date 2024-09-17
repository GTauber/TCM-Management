package tcm.pb.tcmnotification.config;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitAdminConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(RabbitTemplate rabbitTemplate) {
        return new RabbitAdmin(rabbitTemplate);
    }

    @Bean
    public ApplicationRunner declareQueuesAndExchanges(RabbitAdmin rabbitAdmin, RabbitMQConfig rabbitMQConfig) {
        return args -> {
            rabbitAdmin.declareQueue(rabbitMQConfig.notificationQueue());
            rabbitAdmin.declareExchange(rabbitMQConfig.notificationExchange());
            rabbitAdmin.declareBinding(rabbitMQConfig.binding(rabbitMQConfig.notificationQueue(), rabbitMQConfig.notificationExchange()));
        };
    }

}
