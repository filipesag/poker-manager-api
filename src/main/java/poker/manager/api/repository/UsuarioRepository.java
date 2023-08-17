package poker.manager.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import poker.manager.api.domain.Usuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findAllByIsEnabledTrue();
    Optional<Usuario> findByUsername(String username);
}
