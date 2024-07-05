package carmenromano.gestioneeventi.repository;

import carmenromano.gestioneeventi.entities.Evento;
import carmenromano.gestioneeventi.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository  extends JpaRepository<Utente, Long> {
}
