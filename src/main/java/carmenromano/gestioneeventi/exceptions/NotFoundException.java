package carmenromano.gestioneeventi.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(int id) {
        super("Record con id " + id + " non trovato!");

    }
    public NotFoundException(String message) {
        super(message);
    }
}
