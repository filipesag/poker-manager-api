package poker.manager.api.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import poker.manager.api.domain.enums.Permissao;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    @Column(unique = true)
    private String username;
    private String password;
    private String chavePix;
    private String endereco;

    private Boolean isEnabled;

    @Enumerated(EnumType.STRING)
    private Permissao role;

    @OneToMany(mappedBy = "id.usuario")
    private Set<UsuarioPartida> partidas = new HashSet<>();


    public Usuario(){}

    public Usuario(Integer id, String nome, String username, String password, String chavePix, String endereco, Permissao role, Boolean isEnabled, Set<UsuarioPartida> partidas) {
        this.id = id;
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.chavePix = chavePix;
        this.endereco = endereco;
        this.partidas = partidas;
        this.isEnabled = isEnabled;
        this.role = role;
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

    public Permissao getRole() {
        return role;
    }

    public void setRole(Permissao role) {
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
        if(this.role == Permissao.ADMINISTRADOR)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USUARIO"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));
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