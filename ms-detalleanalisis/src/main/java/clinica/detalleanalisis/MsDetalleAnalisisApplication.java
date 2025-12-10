package clinica.detalleanalisis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsDetalleAnalisisApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsDetalleAnalisisApplication.class, args);
    }
}
