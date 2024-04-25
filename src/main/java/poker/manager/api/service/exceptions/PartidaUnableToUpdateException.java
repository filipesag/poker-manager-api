package poker.manager.api.service.exceptions;

public class PartidaUnableToUpdateException extends RuntimeException{
    public PartidaUnableToUpdateException() {
        super("Ops! O status dessa partida não permite alterações.");
    }

    public PartidaUnableToUpdateException(String msg) {
        super(msg);
    }
}
