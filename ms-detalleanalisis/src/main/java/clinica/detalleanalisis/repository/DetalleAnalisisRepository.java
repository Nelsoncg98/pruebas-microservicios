package clinica.detalleanalisis.repository;

import clinica.detalleanalisis.model.DetalleAnalisis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleAnalisisRepository extends JpaRepository<DetalleAnalisis, Long> {
    List<DetalleAnalisis> findByIdAnalisis(Long idAnalisis);
}
