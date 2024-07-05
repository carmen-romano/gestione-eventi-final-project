package carmenromano.gestioneeventi.payloads;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EventoPayload(
        @NotEmpty(message = "Il campo Titolo è obbligatorio")
        String titolo,

        @NotEmpty(message = "Il campo Descrizione è obbligatorio")
        String descrizione,

        @NotNull(message = "Il campo Data è obbligatorio")
        @FutureOrPresent(message = "La data dell'evento deve essere nel futuro o nel presente")
        LocalDate data,

        @NotEmpty(message = "Il campo Luogo è obbligatorio")
        String luogo,

        @NotNull(message = "Il campo Posti Disponibili è obbligatorio")
        @Min(value = 1, message = "Deve esserci almeno un posto disponibile")
        Integer postiDisponibili
) {
}
