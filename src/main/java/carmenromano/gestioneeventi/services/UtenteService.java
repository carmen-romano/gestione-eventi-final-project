package carmenromano.gestioneeventi.services;

import carmenromano.gestioneeventi.entities.Utente;
import carmenromano.gestioneeventi.exceptions.BadRequestException;
import carmenromano.gestioneeventi.exceptions.NotFoundException;
import carmenromano.gestioneeventi.payloads.UtentePayload;
import carmenromano.gestioneeventi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Utente save(UtentePayload body) throws IOException {
        utenteRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + body.email() + " è già stata utilizzata");
        });
        Utente utente = new Utente();
        utente.setNome(body.nome());
        utente.setCognome(body.cognome());
        utente.setEmail(body.email());
        utente.setPassword(passwordEncoder.encode(body.password()));
        utente.setType(body.ruolo());
        return utenteRepository.save(utente);
    }

    public Page<Utente> getAllUtenti(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return utenteRepository.findAll(pageable);
    }

    public Utente findById(int id) {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) {
        Utente found = this.findById(id);
        utenteRepository.delete(found);
    }

    public Utente findByEmail(String email) {
        return utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!!"));
    }

}