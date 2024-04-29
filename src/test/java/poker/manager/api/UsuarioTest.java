package poker.manager.api;
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
import poker.manager.api.repository.UsuarioRepository;
import poker.manager.api.service.UsuarioService;
import org.junit.jupiter.api.Test;
import poker.manager.api.service.exceptions.UsuarioUsernameUsedByAnotherUserException;

import java.util.Collection;
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
    @DisplayName("Criando novo usuário com sucesso")
    public void testCreateNewUserSuccessfuly() {
        given(repository.findByUsername(anyString())).willReturn(Optional.empty());
        given(repository.save(usuario)).willReturn(usuario);
        Usuario novoUsuario = service.inserirNovoUsuario(usuario);
        assertNotNull(novoUsuario);
        assertEquals("User Test", novoUsuario.getNome());
    }

    @Test
    @DisplayName("Criando novo usuário com username já cadastrado")
    public void testCreateNewUserWithUsernameInUse() {
        given(repository.findByUsername(anyString())).willReturn(Optional.of(usuario));
        assertThrows(UsuarioUsernameUsedByAnotherUserException.class, () -> {
            Usuario novoUsuario = service.inserirNovoUsuario(usuario);
        });
        verify(repository, never()).save(any(Usuario.class));
    }








//    @Test
//    @DisplayName("Atualizando usuário com sucesso")
//    public void testUpdateUserSuccessfuly() {
//        given(repository.findByUsername(anyString())).willReturn(Optional.empty());
//        given(repository.save(usuario)).willReturn(usuario);
//
//        Usuario novoUsuario = service.inserirNovoUsuario(usuario);
//        NovoUsuarioDTO novoUsuarioUpdatedDTO = new NovoUsuarioDTO(1,"User Test","userTestUpdated","123456","test@pix.com.br","Rua test 123",true,UserRole.USER,usuarioPartida);
//        Usuario usuarioToBeUpdated = new Usuario(novoUsuarioUpdatedDTO);
//        Usuario usuarioUpdated = service.atualizarUsuario(usuarioToBeUpdated);
//
//        assertEquals("userTestUpdated", usuarioUpdated.getUsername());
//        assertNotNull(usuarioUpdated);
//
//        verify(repository, atLeast(2)).save(any(Usuario.class));
//    }






//    @Test
//    @DisplayName("Criando novo usuário sem inserir senha cadastrada")
//    public void testCreateNewUserWihNoPassword() {
//        given(repository.findByUsername(anyString())).willReturn(Optional.empty());
//        usuario.setPassword("");
//        Usuario novoUsuario = service.inserirNovoUsuario(usuario);
//        verify(repository, never()).save(any(Usuario.class));
//    }
}
