package poker.manager.api.service.exceptions;

public class UsuarioAlreadyInMatchException extends RuntimeException{

    public UsuarioAlreadyInMatchException() {
        super("Sua presença já está confirmada nessa partida.");
    }

    public UsuarioAlreadyInMatchException(String msg) {
        super(msg);
    }

}

