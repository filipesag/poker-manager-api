package poker.manager.api.integration.tests;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ser.Serializers;
import poker.manager.api.domain.Usuario;
import poker.manager.api.dto.UsuarioDTO;
import poker.manager.api.integration.config.TestConfig;
import poker.manager.api.integration.pojo.login.LoginRequest;
import poker.manager.api.integration.pojo.login.LoginResponse;
import poker.manager.api.integration.pojo.usuario.PageResponse;
import poker.manager.api.integration.pojo.usuario.UsuarioRequestResponse;
import poker.manager.api.integration.testcontainers.AbstractIntegrationTest;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UsuarioIntegrationTests extends AbstractIntegrationTest {

    private static String token;

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Usuario usuario;

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
    @DisplayName("Testando busca por todos os usuários")
    public void testGetAllPlayers() throws IOException {
        Response response = given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + this.token)
                .when()
                .get("users/find/all");
        PageResponse<UsuarioDTO> pageResponse = objectMapper.readValue(response.getBody().asString(), PageResponse.class);

        assertNotNull(pageResponse);
        assertEquals(true, pageResponse.getTotalElements() > 0);
    }

    @Test
    @DisplayName("Testando busca por um usuário")
    public void testGetSpecificPlayer() throws IOException {
        UsuarioRequestResponse usuario = given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + this.token)
                .when()
                .get("users/find/1")
                .then()
                .statusCode(200)
                .extract()
                .response().as(UsuarioRequestResponse.class);

        assertNotNull(usuario);
        assertEquals("Filipe Aguiar", usuario.getNome());
        assertEquals("filipesag", usuario.getUsername());
        assertEquals("filipe@hotmail.com", usuario.getChavePix());
        assertEquals("Rua Bambuí 422, ap 302", usuario.getEndereco());
        assertEquals("ADMIN", usuario.getRole());
        assertEquals(true, usuario.getisEnabled());
    }

    @Test
    @DisplayName("Testando atualizar um usuário")
    public void testPutUpdateUser() throws IOException {
        Faker faker = new Faker();
        UsuarioRequestResponse updatedUser = new UsuarioRequestResponse();
        updatedUser.setId(1);
        updatedUser.setNome("Filipe Aguiar");
        updatedUser.setUsername("filipesag");
        updatedUser.setPassword("filipe123");
        updatedUser.setEndereco(faker.address().streetAddress() + faker.address().streetAddressNumber());
        updatedUser.setChavePix(faker.phoneNumber().phoneNumber());

        given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .header("Authorization", "Bearer " + this.token)
                .body(updatedUser)
                .when()
                .put("users/update/1")
                .then()
                .statusCode(204)
                .extract()
                .response();
    }

}
