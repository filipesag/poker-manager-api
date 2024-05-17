package poker.manager.api.integration.pojo.partida;

public class PartidaRequestResponse {
    Integer id;

    Double bucketPorPessoa;

    Integer usuarioAnfitriaoId;

    Integer quantidadeJogadores;

    String status;

    String data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBucketPorPessoa() {
        return bucketPorPessoa;
    }

    public void setBucketPorPessoa(Double bucketPorPessoa) {
        this.bucketPorPessoa = bucketPorPessoa;
    }

    public Integer getUsuarioAnfitriaoId() {
        return usuarioAnfitriaoId;
    }

    public void setUsuarioAnfitriaoId(Integer usuarioAnfitriaoId) {
        this.usuarioAnfitriaoId = usuarioAnfitriaoId;
    }

    public Integer getQuantidadeJogadores() {
        return quantidadeJogadores;
    }

    public void setQuantidadeJogadores(Integer quantidadeJogadores) {
        this.quantidadeJogadores = quantidadeJogadores;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
