package clinica.expedienteclinico;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class MsExpedienteclinicoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsExpedienteclinicoApplication.class, args);
    }
}
