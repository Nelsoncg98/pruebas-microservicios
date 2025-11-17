package clinica.pagos.service;


import clinica.pagos.client.CitaClient;
import clinica.pagos.client.PacienteClient;
import clinica.pagos.dto.CitaDto;
import clinica.pagos.dto.PacienteDto;
import clinica.pagos.dto.PagoDto;
import clinica.pagos.model.Pago;
import clinica.pagos.model.EstadoPago;
import clinica.pagos.model.TipoPago;
import clinica.pagos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoService {
    
    private final PagoRepository pagoRepository;
    private final CitaClient citaClient;
    private final PacienteClient pacienteClient;
    
    @Transactional
    public PagoDto crearPago(PagoDto pagoDto) {
        // Validar que la cita existe
        CitaDto cita = citaClient.obtenerCitaPorPaciente(pagoDto.getPacienteId());
        if (cita == null) {
            throw new RuntimeException("Cita no encontrada para el paciente: " + pagoDto.getPacienteId());
        }
        
        // Validar que el paciente existe
        PacienteDto paciente = pacienteClient.obtenerPaciente(pagoDto.getPacienteId());
        if (paciente == null) {
            throw new RuntimeException("Paciente no encontrado: " + pagoDto.getPacienteId());
        }
        
        // Crear entidad Pago
        Pago pago = new Pago();
        pago.setCitaId(cita.getNumero());
        pago.setPacienteId(pagoDto.getPacienteId());
        pago.setMonto(cita.getCosto());
        pago.setTipoPago(TipoPago.valueOf(pagoDto.getTipoPago()));
        pago.setEstado(EstadoPago.PENDIENTE);
        pago.setFechaPago(LocalDateTime.now());
        
        pago = pagoRepository.save(pago);
        
        return convertirADto(pago, cita, paciente);
    }
    
    @Transactional(readOnly = true)
    public PagoDto obtenerPagoPorId(Long id) {
        Pago pago = pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado: " + id));
        
        CitaDto cita = citaClient.obtenerCitaPorPaciente(pago.getPacienteId());
        PacienteDto paciente = pacienteClient.obtenerPaciente(pago.getPacienteId());
        
        return convertirADto(pago, cita, paciente);
    }
    
    @Transactional(readOnly = true)
    public List<PagoDto> obtenerPagosPorPaciente(Long pacienteId) {
        List<Pago> pagos = pagoRepository.findByPacienteId(pacienteId);
        CitaDto cita = citaClient.obtenerCitaPorPaciente(pacienteId);
        PacienteDto paciente = pacienteClient.obtenerPaciente(pacienteId);
        
        return pagos.stream()
            .map(pago -> convertirADto(pago, cita, paciente))
            .collect(Collectors.toList());
    }
    
    @Transactional
    public PagoDto actualizarEstadoPago(Long id, String nuevoEstado) {
        Pago pago = pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado: " + id));
        
        pago.setEstado(EstadoPago.valueOf(nuevoEstado));
        pago = pagoRepository.save(pago);
        
        CitaDto cita = citaClient.obtenerCitaPorPaciente(pago.getPacienteId());
        PacienteDto paciente = pacienteClient.obtenerPaciente(pago.getPacienteId());
        
        return convertirADto(pago, cita, paciente);
    }
    
    @Transactional(readOnly = true)
    public List<PagoDto> obtenerTodosPagos() {
        return pagoRepository.findAll().stream()
            .map(pago -> {
                CitaDto cita = citaClient.obtenerCitaPorPaciente(pago.getPacienteId());
                PacienteDto paciente = pacienteClient.obtenerPaciente(pago.getPacienteId());
                return convertirADto(pago, cita, paciente);
            })
            .collect(Collectors.toList());
    }
    
    private PagoDto convertirADto(Pago pago, CitaDto cita, PacienteDto paciente) {
        PagoDto dto = new PagoDto();
        dto.setId(pago.getId());
        dto.setCitaId(pago.getCitaId());
        dto.setPacienteId(pago.getPacienteId());
        dto.setMonto(pago.getMonto());
        dto.setTipoPago(pago.getTipoPago().name());
        dto.setEstado(pago.getEstado().name());
        dto.setFechaPago(pago.getFechaPago());
        dto.setCita(cita);
        dto.setPaciente(paciente);
        return dto;
    }
}

    
    


