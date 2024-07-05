package carmenromano.gestioneeventi.controllers;
import carmenromano.gestioneeventi.entities.Evento;
import carmenromano.gestioneeventi.entities.Utente;
import carmenromano.gestioneeventi.exceptions.BadRequestException;
import carmenromano.gestioneeventi.payloads.EventoPayload;
import carmenromano.gestioneeventi.payloads.PrenotazioneResponsePayload;
import carmenromano.gestioneeventi.services.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/eventi")
public class EventoController {
    @Autowired
    EventoService eventoService;

    @GetMapping
    public Page<Evento> getAllEvents(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.eventoService.getAllEvents(page, size, sortBy);
    }

    @GetMapping("/{eventoId}")
    public Evento findById(@PathVariable int eventoId) {
        return eventoService.findById(eventoId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.CREATED)
    public Evento saveEvent(@RequestBody @Valid EventoPayload body, BindingResult validation, @AuthenticationPrincipal Utente currentAuthenticatedUser) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return eventoService.save(body, currentAuthenticatedUser);
    }


    @DeleteMapping("/{eventoId}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable int eventoId) {
        eventoService.findByIdAndDelete(eventoId);
    }
    @PostMapping("/me/prenota/{eventoId}")
    public PrenotazioneResponsePayload prenotaEvento(@PathVariable int eventoId, @AuthenticationPrincipal Utente currentAuthenticatedUser) {
        try {
            eventoService.prenotaEvento(eventoId, currentAuthenticatedUser);
            return new PrenotazioneResponsePayload("Prenotazione effettuata con successo per l'evento con Id:" + eventoId);
        } catch (RuntimeException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}
