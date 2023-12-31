package poker.manager.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import poker.manager.api.dto.PartidaDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.dto.UsuarioPartidaDTO;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "usuario_partida")
public class UsuarioPartida implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private UsuarioPartidaPK id = new UsuarioPartidaPK();

    private Boolean isRebuy = false;
    private Integer colocacao = 0;
    private Double netProFit = 0.00;
    private Integer fichasFinal = 0;

    private Boolean isAnfitriao = false;

    private Boolean isCancelado = false;

    public UsuarioPartida() {
    }

    public UsuarioPartida(Partida partida, Usuario usuario, UsuarioPartidaDTO usuarioPartidaDTO) {
        id.setPartida(partida);
        id.setUsuario(usuario);
        this.isRebuy = usuarioPartidaDTO.isRebuy();
        this.colocacao = usuarioPartidaDTO.colocacao();
        this.netProFit = usuarioPartidaDTO.netProFit();
        this.fichasFinal = usuarioPartidaDTO.fichasFinal();
        this.isAnfitriao = usuarioPartidaDTO.isAnfitriao();
        this.isCancelado = usuarioPartidaDTO.isCancelado();
    }

    public UsuarioPartida(Partida partida, Usuario usuario) {
        id.setPartida(partida);
        id.setUsuario(usuario);
    }

    public UsuarioPartidaPK getId() {
        return id;
    }

    public void setId(UsuarioPartidaPK id) {
        this.id = id;
    }

    public Boolean getRebuy() {
        return isRebuy;
    }

    public void setRebuy(Boolean rebuy) {
        isRebuy = rebuy;
    }

    public Integer getColocacao() {
        return colocacao;
    }

    public void setColocacao(Integer colocacao) {
        this.colocacao = colocacao;
    }

    public Double getNetProFit() {
        return netProFit;
    }

    public void setNetProFit(Double netProFit) {
        this.netProFit = netProFit;
    }

    public Integer getFichasFinal() {
        return fichasFinal;
    }

    public void setFichasFinal(Integer fichasFinal) {
        this.fichasFinal = fichasFinal;
    }

    public Boolean getAnfitriao() {
        return isAnfitriao;
    }

    public void setAnfitriao(Boolean anfitriao) {
        isAnfitriao = anfitriao;
    }

    public Boolean getCancelado() {
        return isCancelado;
    }

    public void setCancelado(Boolean cancelado) {
        isCancelado = cancelado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioPartida that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
