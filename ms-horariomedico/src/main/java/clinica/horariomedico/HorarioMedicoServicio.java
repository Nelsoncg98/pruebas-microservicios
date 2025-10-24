package clinica.horariomedico;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HorarioMedicoServicio {
    @Autowired
    private HorarioMedicoRepositorio repo;
    @Autowired
    private MedicoClient medicoClient;

    public List<HorarioMedico> listar(){
        return repo.findAll();
    }

    public HorarioMedico guardar(HorarioMedico h){
        if (h.getMedicoId() == null){
            throw new IllegalArgumentException("El campo medicoId es obligatorio");
        }
        boolean existe = medicoClient.existeMedicoPorId(h.getMedicoId());
        if(!existe){
            throw new IllegalArgumentException("El médico con id="+h.getMedicoId()+" no existe en ms-medico");
        }
        return repo.save(h);
    }

    public HorarioMedico buscar(Long id){
        Optional<HorarioMedico> op = repo.findById(id);
        return op.orElse(null);
    }

    public void eliminar(Long id){
        repo.deleteById(id);
    }

    public void limpiar(){
        repo.deleteAll();
    }

    public List<HorarioMedico> disponibles(LocalDate fecha, Long medicoId, String consultorio){
        // Estrategia por ID: valida ms-medico si se envía medicoId y filtra por disponible=true
        List<HorarioMedico> base;
        if (medicoId != null && !medicoClient.existeMedicoPorId(medicoId)){
            return List.of();
        }
        if (fecha != null && medicoId != null){
            base = repo.findByFechaAndMedicoIdAndDisponibleTrue(fecha, medicoId);
        } else if (fecha != null){
            base = repo.findByFechaAndDisponibleTrue(fecha);
        } else if (medicoId != null){
            base = repo.findByMedicoIdAndDisponibleTrue(medicoId);
        } else {
            base = repo.findByDisponibleTrue();
        }
        if (consultorio != null && !consultorio.isBlank()){
            base = base.stream().filter(h -> consultorio.equalsIgnoreCase(h.getConsultorio())).toList();
        }
        return base;
    }
}
