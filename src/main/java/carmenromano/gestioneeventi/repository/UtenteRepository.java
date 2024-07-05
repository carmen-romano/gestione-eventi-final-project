package carmenromano.gestioneeventi.repository;

import carmenromano.gestioneeventi.entities.Evento;
import carmenromano.gestioneeventi.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository  extends JpaRepository<Utente, Integer> {
    Optional<Utente> findByEmail(String email);
}
