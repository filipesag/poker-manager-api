package poker.manager.api.integration.pojo.usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import poker.manager.api.domain.Partida;

import java.util.Set;
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioRequestResponse {

    Integer id;
    String nome;

    String username;

    String password;

    String chavePix;

    String endereco;

    String role;

    Boolean isEnabled;

    Set<Partida> partidas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getChavePix() {
        return chavePix;
    }

    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getisEnabled() {
        return isEnabled;
    }

    public void setisEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Set<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(Set<Partida> partidas) {
        this.partidas = partidas;
    }
}
