package poker.manager.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import poker.manager.api.domain.enums.UserRole;
import org.springframework.security.core.userdetails.UserDetails;
import poker.manager.api.dto.NovoUsuarioDTO;
import poker.manager.api.dto.UsuarioDTO;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;
    @Column(unique = true)
    private String username;
    private String password;
    private String chavePix;
    private String endereco;

    private Boolean isEnabled;

    @Enumerated(EnumType.STRING)
    private UserRole role;
    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private Set<UsuarioPartida> partidas = new HashSet<>();


    public Usuario(){}

    public Usuario(NovoUsuarioDTO usuarioDTO) {
        this.id = usuarioDTO.id();
        this.nome = usuarioDTO.nome();
        this.username = usuarioDTO.username();
        this.password = usuarioDTO.password();
        this.chavePix = usuarioDTO.chavePix();
        this.endereco = usuarioDTO.endereco();
        this.partidas = usuarioDTO.partidas();
        this.isEnabled = usuarioDTO.isEnabled();
        this.role = usuarioDTO.role();
    }

    public Usuario(UsuarioDTO usuarioDTO){
        this.id = usuarioDTO.id();
        this.nome = usuarioDTO.nome();
        this.username = usuarioDTO.username();
        this.chavePix = usuarioDTO.chavePix();
        this.endereco = usuarioDTO.endereco();
        this.partidas = usuarioDTO.partidas();
        this.isEnabled = usuarioDTO.isEnabled();
        this.role = usuarioDTO.role();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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


    public Set<UsuarioPartida> getPartidas() {
        return partidas;
    }

    public void setPartidas(Set<UsuarioPartida> partidas) {
        this.partidas = partidas;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("USER"));
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}