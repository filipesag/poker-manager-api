package poker.manager.api.integration.tests.partida;

import com.github.javafaker.Faker;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import poker.manager.api.integration.config.TestConfig;
import poker.manager.api.integration.pojo.partida.PartidaRequestResponse;
import poker.manager.api.integration.pojo.usuario.UsuarioRequestResponse;
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
    private PartidaRequest partidaRequest = new PartidaRequest();

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
        Response response = partidaRequest.buscaPartidaPeloId(specification, this.token);

        PartidaRequestResponse partida = response.then().statusCode(200).extract().body().as(PartidaRequestResponse.class);

        assertNotNull(partida);
        assertEquals(2, partida.getUsuarioAnfitriaoId());
        assertEquals(5, partida.getQuantidadeJogadores());
        assertEquals("ABERTA", partida.getStatus());
        assertEquals("12/09/2023", partida.getData());
    }

    @Test
    @DisplayName("Testanto cancelar partida")
    void testCallOffMatch() {
        Response response = partidaRequest.buscaPartidaPeloId(specification, this.token);
        PartidaRequestResponse partidaCancelada = response.then().statusCode(200).extract().body().as(PartidaRequestResponse.class);
        Response responseOfCallOff = partidaRequest.cancelarPartida(specification, this.token, partidaCancelada);

        assertEquals(204, responseOfCallOff.getStatusCode());
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

        PartidaRequestResponse partidaCriada = partidaRequest.criaNovaPartida(specification, token, partida)
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
        Response response = partidaRequest.buscaPartidaPeloId(specification, this.token);
        PartidaRequestResponse partidaRequestResponse = response.then().statusCode(200).extract().body().as(PartidaRequestResponse.class);
        Response responseOfStart = partidaRequest.comecarPartida(specification, this.token,partidaRequestResponse);

        assertEquals(204, responseOfStart.getStatusCode());
    }
}
