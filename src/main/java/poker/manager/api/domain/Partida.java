package poker.manager.api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import poker.manager.api.domain.enums.PartidaStatus;
import poker.manager.api.dto.NovaPartidaDTO;
import poker.manager.api.dto.PartidaDTO;

import java.io.Serializable;
import java.time.LocalDate;

import java.util.HashSet;

import java.util.Set;


@Entity
@Table(name = "partida")
public class Partida implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "my_seq_gen")
    @SequenceGenerator(name = "my_seq_gen", sequenceName = "GEN_SEQUENCE", allocationSize = 1)
    private Integer id;

    @JsonIgnore
    @OneToMany(mappedBy = "partida")
    private Set<UsuarioPartida> jogadores = new HashSet<>();

    private Double bucketPorPessoa;
    private Integer usuarioAnfitriaoId;
    private Integer quantidadeJogadores;

    @JsonProperty("data")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private PartidaStatus status;

    public Partida() {
    }

    public Partida(PartidaDTO partidaDTO) {
        this.id = partidaDTO.id();
        this.bucketPorPessoa = partidaDTO.bucketPorPessoa();
        this.usuarioAnfitriaoId = partidaDTO.usuarioAnfitriaoId();
        this.quantidadeJogadores = partidaDTO.quantidadeJogadores();
        this.data = partidaDTO.data();
        this.status = partidaDTO.status();
    }

    public Partida(NovaPartidaDTO novaPartidaDTO) {
        this.id = novaPartidaDTO.id();
        this.bucketPorPessoa = novaPartidaDTO.bucketPorPessoa();
        this.quantidadeJogadores = novaPartidaDTO.quantidadeJogadores();
        this.data = novaPartidaDTO.data();
    }

    public Integer getId () {
        return id;
    }

    public void setId (Integer id){
        this.id = id;
    }

    @JsonIgnore
    public Set<UsuarioPartida> getJogadores () {
        Set<UsuarioPartida> list = new HashSet<>();
        for (UsuarioPartida x : jogadores) {
            list.add(x);
        }
        return list;
    }

    public void setJogadores (Set <UsuarioPartida> usuarios) {
        this.jogadores = usuarios;
    }

    public Integer getQuantidadeJogadores () {
        return quantidadeJogadores;
    }

    public void setQuantidadeJogadores (Integer quantidadeJogadores){
        this.quantidadeJogadores = quantidadeJogadores;
    }

    public LocalDate getData () {
        return data;
    }

    public void setData (LocalDate data){
        this.data = data;
    }

    public PartidaStatus getStatus () {
        return status;
    }

    public void setStatus (PartidaStatus status){
        this.status = status;
    }


    public Integer getUsuarioAnfitriaoId () {
        return usuarioAnfitriaoId;
    }

    public Double getBucketPorPessoa() {
        return bucketPorPessoa;
    }

    public void setBucketPorPessoa(Double bucketPorPessoa) {
        this.bucketPorPessoa = bucketPorPessoa;
    }

    public void setUsuarioAnfitriaoId (Integer usuarioAnfitriaoId){
        this.usuarioAnfitriaoId = usuarioAnfitriaoId;
    }
}

