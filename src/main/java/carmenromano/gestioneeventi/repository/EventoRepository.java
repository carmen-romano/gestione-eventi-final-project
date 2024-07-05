package carmenromano.gestioneeventi.repository;

import carmenromano.gestioneeventi.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
