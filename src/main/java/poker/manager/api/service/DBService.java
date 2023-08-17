package poker.manager.api.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import poker.manager.api.domain.Partida;
import poker.manager.api.domain.Usuario;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.domain.enums.PartidaStatus;
import poker.manager.api.domain.enums.Permissao;
import poker.manager.api.repository.PartidaRepository;
import poker.manager.api.repository.UsuarioPartidaRepository;
import poker.manager.api.repository.UsuarioRepository;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class DBService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private UsuarioPartidaRepository usuarioPartidaRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public void instantiateTestDB() throws ParseException {
        List<Usuario> usuarioList = new ArrayList<Usuario>();
        
        usuarioList.add(new Usuario(null, "Filipe da Silva Aguiar", "filipe", encoder.encode("filipe123"),
        "filipesag@gmail.com", "Rua bambuí 422, ap 302", Permissao.ADMINISTRADOR,true, null));

        usuarioList.add(new Usuario(null, "Thalison Neves", "thalis", encoder.encode("thalis123"),
        "thalison@gmail.com", "Rua mangabeira 202, ap 101", Permissao.ADMINISTRADOR,true, null));
        
        usuarioList.add(new Usuario(null, "Vinicius Conceição", "vini", encoder.encode("vini123"),
        "vini@gmail.com", "Rua cobre 225, ap 202", Permissao.USUARIO,true, null));


        usuarioList.add(new Usuario(null, "Thaylon Luan", "thaylin", encoder.encode("thaylon123"),
                "thaylon@gmail.com", "Rua coronel 212, ap 202", Permissao.USUARIO,true, null));
        usuarioRepository.saveAll(usuarioList);

        LocalDate date = LocalDate.parse("23/08/2023 20:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));





        Partida partida1 =  new Partida(null, usuarioList.get(0).getId(), 4, date, PartidaStatus.ABERTA);
        partidaRepository.save(partida1);

        List<UsuarioPartida> usuarioPartidas = new ArrayList<>();
        usuarioPartidas.add(new UsuarioPartida(partida1 ,usuarioList.get(0),false,1,38.90,5000,true,false));
        usuarioPartidas.add(new UsuarioPartida(partida1 ,usuarioList.get(1),false,1,38.90,5000,true,false));
        usuarioPartidas.add(new UsuarioPartida(partida1 ,usuarioList.get(2),false,1,38.90,5000,true,false));
        usuarioPartidas.add(new UsuarioPartida(partida1 ,usuarioList.get(3),false,1,38.90,5000,true,false));
        usuarioPartidaRepository.saveAll(usuarioPartidas);

    };
}
