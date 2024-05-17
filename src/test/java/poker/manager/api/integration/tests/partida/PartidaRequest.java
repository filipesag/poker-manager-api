package poker.manager.api.integration.tests.partida;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import poker.manager.api.domain.UsuarioPartida;
import poker.manager.api.integration.config.TestConfig;
import poker.manager.api.integration.pojo.partida.PartidaRequestResponse;

import static io.restassured.RestAssured.given;

public class PartidaRequest {

    public Response buscaPartidaPeloId(RequestSpecification specification, String token) {
        return given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("match/-1");
    }

    public Response criaNovaPartida(RequestSpecification specification, String token, PartidaRequestResponse partida){
        return given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + token)
                .body(partida)
                .when()
                .post("/match/creation");
    }

    public Response cancelarPartida(RequestSpecification specification, String token, PartidaRequestResponse partida){
        return given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + token)
                .body(partida)
                .when()
                .put("match/calloff-match");
    }

    public Response comecarPartida(RequestSpecification specification, String token, PartidaRequestResponse partida) {
        return  given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + token)
                .body(partida)
                .when()
                .put("match/start-match");
    }
}
