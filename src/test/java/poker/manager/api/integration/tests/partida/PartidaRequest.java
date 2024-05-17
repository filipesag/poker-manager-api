package poker.manager.api.integration.tests.partida;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import poker.manager.api.integration.config.TestConfig;

import static io.restassured.RestAssured.given;

public class PartidaRequest {

    public Response getPartida(RequestSpecification specification, String token) {
        return given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("match/1");
    }
}
