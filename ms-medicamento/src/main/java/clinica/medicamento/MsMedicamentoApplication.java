package clinica.medicamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsMedicamentoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsMedicamentoApplication.class, args);
    }
}
