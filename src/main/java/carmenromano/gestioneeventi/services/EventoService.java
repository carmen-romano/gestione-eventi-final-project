package carmenromano.gestioneeventi.services;
import carmenromano.gestioneeventi.entities.Evento;
import carmenromano.gestioneeventi.entities.Utente;
import carmenromano.gestioneeventi.exceptions.NotFoundException;
import carmenromano.gestioneeventi.payloads.EventoPayload;
import carmenromano.gestioneeventi.repository.EventoRepository;
import carmenromano.gestioneeventi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public Evento save(EventoPayload body, Utente organizer) throws IOException {
        Evento evento = new Evento();
        evento.setTitolo(body.titolo());
        evento.setDescrizione(body.descrizione());
        evento.setData(body.data());
        evento.setLuogo(body.luogo());
        evento.setPostiDisponibili(body.postiDisponibili());
        evento.setOrganizzatore(organizer);
        return eventoRepository.save(evento);
    }

    public Page<Evento> getAllEvents(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        return eventoRepository.findAll(pageable);
    }

    public Evento findById(int id) {
        return eventoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) {
        Evento found = this.findById(id);
        eventoRepository.delete(found);
    }
    public Evento findByIdAndUpdate(int id, Evento body) {
        Optional<Evento> eventOptional = eventoRepository.findById(id);
        if (eventOptional.isPresent()) {
            Evento found = eventOptional.get();
            found.setTitolo(body.getTitolo());
            found.setDescrizione(body.getDescrizione());
            found.setData(body.getData());
            found.setLuogo(body.getLuogo());
            found.setPostiDisponibili(body.getPostiDisponibili());
            return eventoRepository.save(found);
        } else {
            throw new NotFoundException(id);
        }
    }

    public void prenotaEvento(int eventoId, Utente utente) {
        Evento evento = findById(eventoId);


        int utenteId = utente.getId();
        for (Utente partecipante : evento.getPartecipanti()) {
            if (partecipante.getId() == utenteId) {
                throw new RuntimeException("Hai già una prenotazione per questo evento");
            }
        }
        if (evento.getPostiDisponibili() <= 0) {
            throw new RuntimeException("Non ci sono posti disponibili per questo evento");
        }

        evento.getPartecipanti().add(utente);
        evento.setPostiDisponibili(evento.getPostiDisponibili() - 1);
        eventoRepository.save(evento);
    }

}