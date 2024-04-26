package poker.manager.api.service.exceptions;

public class UsuarioUsernameUsedByAnotherUserException extends RuntimeException{
    public UsuarioUsernameUsedByAnotherUserException() {
        super("Ops! Este username já está sendo usado.");
    }

    public UsuarioUsernameUsedByAnotherUserException(String msg) {
        super(msg);
    }
}
