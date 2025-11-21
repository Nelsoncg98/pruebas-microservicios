package clinica.pagos.service;

import clinica.pagos.client.CitaClient;
import clinica.pagos.client.PacienteClient;
import clinica.pagos.dto.CitaDto;
import clinica.pagos.dto.PacienteDto;
import clinica.pagos.model.*;
import clinica.pagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Servicio de Pago - Pasos 4 y 5 del flujo: "Cajero procesa pago" y
 * "Confirmación de pago"
 * Aplica todas las validaciones de reglas de negocio
 */
@Service
public class PagoService {
    // ... resto del código igual
}