package carmenromano.gestioneeventi.payloads;

import jakarta.validation.constraints.NotEmpty;

public record UtenteLoginPayload(@NotEmpty(message = "Il campo Email è obbligatorio!")
String email,
                                 @NotEmpty(message = "Il campo Password è obbligatorio!d")
                                 String password) {
}
