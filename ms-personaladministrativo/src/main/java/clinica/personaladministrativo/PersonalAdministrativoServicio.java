package clinica.personaladministrativo;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonalAdministrativoServicio {
    private final PersonalAdministrativoRepositorio repo;

    public PersonalAdministrativoServicio(PersonalAdministrativoRepositorio repo) {
        this.repo = repo;
    }

    public List<PersonalAdministrativo> listar() { return repo.findAll(); }
    public PersonalAdministrativo buscar(Long id) { return repo.findById(id).orElse(null); }
    public PersonalAdministrativo guardar(PersonalAdministrativo p) { return repo.save(p); }
    public PersonalAdministrativo actualizar(Long id, PersonalAdministrativo p) {
        p.setNumero(id);
        return repo.save(p);
    }
    public void eliminar(Long id) { repo.deleteById(id); }
}
