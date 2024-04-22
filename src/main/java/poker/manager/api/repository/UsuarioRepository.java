package poker.manager.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.dto.UsuarioDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Page<Usuario> findAllByIsEnabledTrue(Pageable pageable);
    Optional<Usuario> findByUsername(String username);

    @Query(value = "SELECT u.*\n" +
            "FROM usuario u\n" +
            "JOIN usuario_partida up ON u.id = up.usuario_id\n" +
            "JOIN partida p ON up.partida_id = p.id\n" +
            "WHERE p.status = 'INICIADA';", nativeQuery = true)
    Set<Usuario> findAllInMatchStarted();

}
