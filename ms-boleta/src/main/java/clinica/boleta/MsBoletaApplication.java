package clinica.boleta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsBoletaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsBoletaApplication.class, args);
    }
}
