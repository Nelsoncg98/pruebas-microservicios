package clinica.pagos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuración de beans de la aplicación
 */
@Configuration
public class AppConfig {
    
    /**
     * Bean de RestTemplate para consumir otros microservicios
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
