package carmenromano.gestioneeventi.services;

import carmenromano.gestioneeventi.entities.Utente;
import carmenromano.gestioneeventi.exceptions.UnauthorizedException;
import carmenromano.gestioneeventi.payloads.UtenteLoginPayload;
import carmenromano.gestioneeventi.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateAndGenerateToken(UtenteLoginPayload payload) {
        Utente utente = this.utenteService.findByEmail(payload.email());
        if (passwordEncoder.matches(payload.password(), utente.getPassword())) {
            return jwtTools.createToken(utente);
        } else {
            throw new UnauthorizedException("Email o password non corretta!");
        }
    }
}