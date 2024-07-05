package carmenromano.gestioneeventi.controllers;

import carmenromano.gestioneeventi.entities.Evento;
import carmenromano.gestioneeventi.entities.Utente;
import carmenromano.gestioneeventi.exceptions.BadRequestException;
import carmenromano.gestioneeventi.payloads.PrenotazioneResponsePayload;
import carmenromano.gestioneeventi.services.EventoService;
import carmenromano.gestioneeventi.services.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private EventoService eventoService;

    @GetMapping
    public Page<Utente> getAllUtenti(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.utenteService.getAllUtenti(page, size, sortBy);
    }

    @GetMapping("/{utenteId}")
    public Utente findById(@PathVariable int utenteId) {
        return this.utenteService.findById(utenteId);
    }

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente currentAuthenticatedUser) {
        this.utenteService.findByIdAndDelete(currentAuthenticatedUser.getId());

    }

    @PostMapping("/me/prenota/{eventoId}")
    public PrenotazioneResponsePayload prenotaEvento(@PathVariable int eventoId, @AuthenticationPrincipal Utente currentAuthenticatedUser) {
        try {
            eventoService.prenotaEvento(eventoId, currentAuthenticatedUser);
            return new PrenotazioneResponsePayload("Prenotazione effettuata con successo.");
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}

