package poker.manager.api.service.exceptions;

public class PartidaStatusRepeatedException extends RuntimeException{

    public PartidaStatusRepeatedException() { super("Ops! Obtivemos um erro ao retornar a partida em específico");}

    public PartidaStatusRepeatedException(String msg) {
        super(msg);
    }


}
