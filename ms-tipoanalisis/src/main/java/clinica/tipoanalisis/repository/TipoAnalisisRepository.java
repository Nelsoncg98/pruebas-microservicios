package clinica.tipoanalisis.repository;

import clinica.tipoanalisis.model.TipoAnalisis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAnalisisRepository extends JpaRepository<TipoAnalisis, Long> {
}
