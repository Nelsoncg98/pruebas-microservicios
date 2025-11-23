package Clinica.pagos.service;

import Clinica.pagos.model.Pago;
import Clinica.pagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class PagoService {

	@Autowired
	private PagoRepository repo;

	@Autowired
	private RestTemplate restTemplate;

	// Crea un pago; obtiene el monto desde ms-cita por idCita
	public Pago crear(Pago pago){
		if (pago.getIdCita() == null){
			throw new IllegalArgumentException("Se requiere idCita");
		}

		// Llamada simple a ms-cita para obtener costo de la cita
		try{
			String url = "http://localhost:8089/cita/buscar/" + pago.getIdCita();
			ResponseEntity<Map> resp = restTemplate.getForEntity(url, Map.class);
			if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null){
				Map body = resp.getBody();
				Object costoObj = body.get("costo");
				double costo = 0.0;
				if (costoObj instanceof Number){
					costo = ((Number) costoObj).doubleValue();
				} else if (costoObj != null){
					try{ costo = Double.parseDouble(costoObj.toString()); } catch(Exception e){}
				}
				pago.setMontopagar(costo);
			}
		} catch(Exception e){
			// Si falla la llamada, dejamos monto como estaba o 0
		}

		if (pago.getFecha() == null){
			pago.setFecha(LocalDateTime.now());
		}

		if (pago.getEstadoPago() == null || pago.getEstadoPago().isBlank()){
			pago.setEstadoPago("Pendiente");
		}

		return repo.save(pago);
	}

	public Optional<Pago> buscar(Long id){
		return repo.findById(id);
	}
}

