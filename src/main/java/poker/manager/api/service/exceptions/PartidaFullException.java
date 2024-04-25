package poker.manager.api.service.exceptions;

public class PartidaFullException extends RuntimeException{
    public PartidaFullException() {
        super("Ops! Está partida já está cheia...");
    }

    public PartidaFullException(String msg) {
        super(msg);
    }

}
