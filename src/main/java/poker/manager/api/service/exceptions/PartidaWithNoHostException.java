package poker.manager.api.service.exceptions;

public class PartidaWithNoHostException extends RuntimeException{

    public PartidaWithNoHostException() {
        super("Ops! Está partida está sem anfitrião. Por favor, aguarde a sua definição...");
    }

    public PartidaWithNoHostException(String msg) {
        super(msg);
    }

}
