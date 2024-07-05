package carmenromano.gestioneeventi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "eventi")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String descrizione;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private String luogo;

    @Column(nullable = false)
    private int postiDisponibili;

    @ManyToOne
    @JoinColumn(name = "organizzatore_id")
    @JsonIgnore
    private Utente organizzatore;

    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "utente_evento",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "utente_id"))
    private Set<Utente> partecipanti = new HashSet<>();
}