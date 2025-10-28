package clinica.horariomedico;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173") // habilitar CORS para el frontend en desarrollo
@RestController
@RequestMapping("/horariomedico")
public class HorarioMedicoControl {

    @Autowired
    private HorarioMedicoServicio serv;

    @GetMapping("/listar")
    public ResponseEntity<List<HorarioMedico>> listar(){
        return ResponseEntity.ok(serv.listar());
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody HorarioMedico h){
        try{
            HorarioMedico saved = serv.guardar(h);


            URI location = URI.create("/horariomedico/buscar/" + saved.getNumero());


            return ResponseEntity.created(location).body(saved); // 201 Created
            
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage())); // 400
        } catch (IllegalStateException e){
            return ResponseEntity.status(409).body(Map.of("message", e.getMessage())); // 409 Conflict
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id){
        HorarioMedico h = serv.buscar(id);
        if (h == null){
            return ResponseEntity.status(404).body(Map.of("message", "Horario no encontrado"));
        }
        return ResponseEntity.ok(h);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            serv.eliminar(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (EmptyResultDataAccessException ex){
            return ResponseEntity.status(404).body(Map.of("message", "Horario no encontrado"));
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@RequestParam Long id, @RequestBody HorarioMedico h){
        try{
            HorarioMedico updated = serv.actualizar(id, h);
            return ResponseEntity.ok(updated); // 200
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage())); // 400
        } catch (IllegalStateException e){
            return ResponseEntity.status(409).body(Map.of("message", "Conflicto en el estado del recurso por " + e.getMessage())); // 409 Conflict
        }
    }

    @DeleteMapping("/limpiar")
    public ResponseEntity<Void> limpiar(){
        serv.limpiar();
        return ResponseEntity.noContent().build(); // 204
    }

    @GetMapping("/disponibles") // lista de horarios disponibles con filtros opcionales
    public ResponseEntity<List<HorarioMedico>> disponibles(
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate fecha,
        @RequestParam(required = false) Long medicoId,
        @RequestParam(required = false) String consultorio
    ){
        return ResponseEntity.ok(serv.disponibles(fecha, medicoId, consultorio));
    }

    @GetMapping("/medicosdisponibles") // lista de médicos disponibles en un rango de hora en una fecha dada
    public ResponseEntity<List<?>> medicosDisponibles(
        @RequestParam(required = true) 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
        LocalDate fecha,
        @RequestParam(required = true) LocalTime horaInicio,
        @RequestParam(required = true) LocalTime horaFin
    ) {
        return ResponseEntity.ok(serv.medicosDisponibles(fecha, horaInicio, horaFin));
    }
    

}
