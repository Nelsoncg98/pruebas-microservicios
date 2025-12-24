package clinica.cajero.repository;

import clinica.cajero.model.Cajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CajeroRepository extends JpaRepository<Cajero, Long> {
}
