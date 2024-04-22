package poker.manager.api.domain;

import java.io.Serializable;

public class UsuarioPartidaId implements Serializable {
    private Integer usuarioId;
    private Integer partidaId;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getPartidaId() {
        return partidaId;
    }

    public void setPartidaId(Integer partidaId) {
        this.partidaId = partidaId;
    }

    public UsuarioPartidaId() {}

    public UsuarioPartidaId(Integer usuarioId, Integer partidaId) {
        this.usuarioId = usuarioId;
        this.partidaId = partidaId;
    }
}
