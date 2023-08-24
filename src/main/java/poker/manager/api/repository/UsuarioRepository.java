package poker.manager.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import poker.manager.api.domain.Usuario;
import poker.manager.api.dto.UsuarioDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Page<Usuario> findAllByIsEnabledTrue(Pageable pageable);
    Optional<Usuario> findByUsername(String username);

}
