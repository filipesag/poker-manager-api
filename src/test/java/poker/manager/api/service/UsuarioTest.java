package poker.manager.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.domain.enums.UserRole;
import poker.manager.api.dto.NovoUsuarioDTO;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.repository.UsuarioRepository;
import poker.manager.api.service.UsuarioService;
import org.junit.jupiter.api.Test;
import poker.manager.api.service.exceptions.UsuarioUsernameUsedByAnotherUserException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class UsuarioTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;

    private NovoUsuarioDTO novoUsuarioDTO;

    private Set<UsuarioPartida> usuarioPartida;

    @BeforeEach
    public void setup() {
        novoUsuarioDTO = new NovoUsuarioDTO(1, "User Test", "usertest", "123456", "test@pix.com.br", "Rua test 123", true, UserRole.USER, usuarioPartida);
        usuario = new Usuario(novoUsuarioDTO);
    }


    @Test
    @DisplayName("Testando criando novo usuário com sucesso")
    public void testCreateNewUserSuccessfuly() {
        given(repository.findByUsername(anyString())).willReturn(Optional.empty());
        given(repository.save(usuario)).willReturn(usuario);
        Usuario novoUsuario = service.inserirNovoUsuario(usuario);
        assertNotNull(novoUsuario);
        assertEquals("User Test", novoUsuario.getNome());
    }

    @Test
    @DisplayName("Testando atualizando novo usuário com sucesso")
    public void testUpdateNewUserSuccessfuly() {
        NovoUsuarioDTO novoUsuarioDTO2 = new NovoUsuarioDTO(1, "User Test Updated", "usertest", "123456", "test@pix.com.br", "Rua test 123", true, UserRole.USER, usuarioPartida);
        Usuario usuario2 = new Usuario(novoUsuarioDTO2);
        given(repository.findByUsername(anyString())).willReturn(Optional.of(usuario));
        given(repository.save(usuario2)).willReturn(usuario2);
        given(repository.findById(1)).willReturn(Optional.of(usuario2));

        Usuario novoUsuarioUpdated = service.atualizarUsuario(usuario2);

        assertNotNull(novoUsuarioUpdated);
        assertEquals("User Test Updated", novoUsuarioUpdated.getNome());
    }

    @Test
    @DisplayName("Testando atualizando novo usuário com username cadastrado")
    public void testUpdateNewUserWithUsernameInUse() {
        NovoUsuarioDTO novoUsuarioDTO2 = new NovoUsuarioDTO(2, "User Test Two", "usertest", "123456", "test@pix.com.br", "Rua test 123", true, UserRole.USER, usuarioPartida);
        Usuario usuario2 = new Usuario(novoUsuarioDTO2);
        given(repository.findByUsername(usuario2.getUsername())).willReturn(Optional.of(usuario2));

        assertThrows(UsuarioUsernameUsedByAnotherUserException.class, () -> {
            service.atualizarDados(usuario, usuario2);
        });
    }

    @Test
    @DisplayName("Testando criando novo usuário com username já cadastrado")
    public void testCreateNewUserWithUsernameInUse() {
        given(repository.findByUsername(anyString())).willReturn(Optional.of(usuario));
        assertThrows(UsuarioUsernameUsedByAnotherUserException.class, () -> {
            Usuario novoUsuario = service.inserirNovoUsuario(usuario);
        });
        verify(repository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Testando busca todos em partida iniciada")
    public void testFindAllInStartedMatchSuccessfuly() {
        Set<UsuarioPartida> usuarioPartida2 = new HashSet<>();
        Usuario usuario2 = new Usuario(new UsuarioDTO(2,"User Test Two", "usertesttwo", "pix2@hotmail.com", "Rua test2 123", UserRole.USER,true, usuarioPartida2));
        given(repository.buscaTodosEmPartidaIniciada()).willReturn(Set.of(usuario, usuario2));

        Set<Usuario> players = service.buscaTodosEmPartidaIniciada();

        assertNotNull(players);
        assertEquals(2, players.size());
        verify(repository, times(1)).buscaTodosEmPartidaIniciada();
    }
}
