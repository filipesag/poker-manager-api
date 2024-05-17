package poker.manager.api.integration.tests.usuario;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import poker.manager.api.integration.config.TestConfig;
import poker.manager.api.integration.pojo.usuario.UsuarioRequestResponse;

import static io.restassured.RestAssured.given;

public class UsuarioRequest {

    public Response listarTodos(RequestSpecification specification, String token) {
        return given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("users/find/all");
    }

    public Response listaUsuarioEspecifico(RequestSpecification specification, String token) {
        return given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("users/find/-1");
    }

    public Response criaNovoUsuario(RequestSpecification specification, String token, UsuarioRequestResponse novoUsuario) {
        return given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + token)
                .body(novoUsuario)
                .when()
                .post("/users/new/player");
    }
}
