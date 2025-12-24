package clinica.cajero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsCajeroApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsCajeroApplication.class, args);
    }
}
