package tcm.pb.tcmnotificationconsumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class TcmNotificationConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcmNotificationConsumerApplication.class, args);
    }

}
