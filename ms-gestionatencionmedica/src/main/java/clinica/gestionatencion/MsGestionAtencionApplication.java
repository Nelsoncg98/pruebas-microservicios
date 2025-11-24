package clinica.gestionatencion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsGestionAtencionApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsGestionAtencionApplication.class, args);
    }
}
