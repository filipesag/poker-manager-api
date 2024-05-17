package poker.manager.api.integration.tests.partida;

import com.github.javafaker.Faker;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import poker.manager.api.integration.config.TestConfig;
import poker.manager.api.integration.pojo.partida.PartidaRequestResponse;
import poker.manager.api.integration.testcontainers.AbstractIntegrationTest;
import poker.manager.api.integration.tests.BaseTests;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PartidaIntegrationTests extends AbstractIntegrationTest {

    private static String token;
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void setup() {
        specification = BaseTests.getRequestSpecification();
        objectMapper = BaseTests.getObjectMapper();
    }

    @BeforeEach
    public void login() {
        token = BaseTests.login();
    }

    @Test
    @DisplayName("Testanto busca de partida pelo id")
    void testGetMatchById() {
        PartidaRequestResponse partidaRequestResponse = given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + this.token)
                .when()
                .get("match/1")
                .then()
                .statusCode(200)
                .extract()
                .as(PartidaRequestResponse.class);

        assertNotNull(partidaRequestResponse);
        assertEquals("ABERTA", partidaRequestResponse.getStatus().toString());
    }

    @Test
    @DisplayName("Testanto cancelar partida")
    void testCallOffMatch() {
        PartidaRequestResponse partidaCancelada = given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + this.token)
                .when()
                .get("match/1")
                .then()
                .statusCode(200)
                .extract()
                .response().as(PartidaRequestResponse.class);

        given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + this.token)
                .body(partidaCancelada)
                .when()
                .put("match/calloff-match")
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Testanto criação de nova partida")
    void testCreateNewMatch() {
        Faker faker = new Faker(new Locale("pt-BR"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        Date dataFutura = faker.date().future(10, TimeUnit.DAYS);
        String dataFuturaFormatada = sdf.format(dataFutura);

        PartidaRequestResponse partida = new PartidaRequestResponse();

        partida.setBucketPorPessoa(30.0);
        partida.setQuantidadeJogadores(5);
        partida.setData(dataFuturaFormatada);

        PartidaRequestResponse partidaCriada = given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + this.token)
                .body(partida)
                .when()
                .post("/match/creation")
                .then()
                .statusCode(201)
                .extract()
                .response().as(PartidaRequestResponse.class);

        assertEquals(30.0, partidaCriada.getBucketPorPessoa());
        assertEquals(0, partidaCriada.getUsuarioAnfitriaoId());
        assertEquals(5, partidaCriada.getQuantidadeJogadores());
    }

    @Test
    @DisplayName("Testanto início de partida")
    void testStartMatch() {
        PartidaRequestResponse partidaRequestResponse = given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + this.token)
                .when()
                .get("match/1")
                .then()
                .statusCode(200)
                .extract()
                .response().as(PartidaRequestResponse.class);

        given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + this.token)
                .body(partidaRequestResponse)
                .when()
                .put("match/start-match")
                .then()
                .statusCode(204);

    }
}
