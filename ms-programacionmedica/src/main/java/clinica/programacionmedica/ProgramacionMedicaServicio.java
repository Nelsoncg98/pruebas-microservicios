package clinica.programacionmedica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.client.RestTemplate;

@Service
public class ProgramacionMedicaServicio {
    @Autowired
    private ProgramacionMedicaRepositorio repo;

    @Autowired
    private RestTemplate resTem;

    private String fecha() {
        Date dat = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(dat);
    }

    public boolean existeAdministrativo(Long idAdministrativo) {
        String url = "http://ms-personaladministrativo/personaladministrativo/buscar/" + idAdministrativo;
        Object resp = resTem.getForObject(url, Object.class);
        return resp != null;
    }

    public ProgramacionMedica nueva(Long idAdministrativo) {

        if (!existeAdministrativo(idAdministrativo)) {
            throw new IllegalArgumentException("El administrativo con id=" + idAdministrativo + " no existe");
        }

        HorarioMedico[] horarios = resTem.getForObject("http://ms-carritohorariomedico/carritohorario/listar",
                HorarioMedico[].class);
        List<Long> idsGuardados = new ArrayList<>();
        List<HorarioMedico> horariosCompletos = new ArrayList<>();

        // guardar los horarios medicos
        for (HorarioMedico h : horarios) {
            HorarioMedico guardado = resTem.postForObject("http://ms-horariomedico/horariomedico/guardar", h,
                    HorarioMedico.class);

            idsGuardados.add(guardado.getNumero());
            horariosCompletos.add(guardado);
        }

        ProgramacionMedica p = new ProgramacionMedica(idAdministrativo, fecha(), true, idsGuardados);
        // Persistir la programación antes de limpiar el carrito
        ProgramacionMedica guardada = repo.save(p);

        guardada.setHorarios(horariosCompletos);

        resTem.delete("http://ms-carritohorariomedico/carritohorario/nuevo");

        return guardada;
    }

    public ProgramacionMedica buscar(Long id) {
        ProgramacionMedica op = repo.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Programación médica no encontrada"));

        // Llenar los horarios completos
        List<HorarioMedico> horarios = new ArrayList<>();
        for (Long horarioid : op.getHorarioMedicoIds()) {
            HorarioMedico h = resTem.getForObject("http://ms-horariomedico/horariomedico/buscar/" + horarioid,
                    HorarioMedico.class);
            if (h != null) {
                horarios.add(h);
            }

        }
        op.setHorarios(horarios);

        return op;
    }

    public Page<ProgramacionMedica> listar(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ProgramacionMedica> pagina = repo.findAll(pageable);

        for (ProgramacionMedica p : pagina.getContent()) {
            List<HorarioMedico> horarios = new ArrayList<>();
            for (Long horarioid : p.getHorarioMedicoIds()) {
                HorarioMedico h = resTem.getForObject("http://ms-horariomedico/horariomedico/buscar/" + horarioid,
                        HorarioMedico.class);
                if (h != null) {
                    horarios.add(h);
                }
            }
            p.setHorarios(horarios);
        }

        return pagina;
    }

    public void eliminar(Long id) {
        // Inactivar en lugar de eliminar físicamente
        ProgramacionMedica p = buscar(id);
        if (p != null) {
            p.setActivo(false);
            repo.save(p);
        }
    }

    public void limpiar() {
        repo.deleteAll();
    }

    public ProgramacionMedica reactivar(Long id) {
        ProgramacionMedica p = buscar(id);
        if (p == null)
            return null;
        p.setActivo(true);
        return repo.save(p);
    }
}
