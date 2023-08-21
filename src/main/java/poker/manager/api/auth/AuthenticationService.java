package poker.manager.api.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import poker.manager.api.config.JwtService;
import poker.manager.api.domain.Usuario;
import poker.manager.api.repository.UsuarioRepository;

@Service
public class AuthenticationService {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtService jwtService;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        Usuario usuario = repository.findByUsername(request.getUsername())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(usuario);
        AuthenticationResponse authResp = new AuthenticationResponse(jwtToken);
        return authResp;
    }
}
