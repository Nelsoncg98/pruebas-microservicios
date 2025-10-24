package clinica.horariomedico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class MedicoClient {
    @Autowired
    private RestTemplate restTemplate;

    public boolean existeMedicoPorId(Long id){
        try{
            Object resp = restTemplate.getForObject("http://ms-medico/medico/buscar/{id}", Object.class, id);
            return resp != null;
        } catch (RestClientException ex){
            return false;
        }
    }
}
