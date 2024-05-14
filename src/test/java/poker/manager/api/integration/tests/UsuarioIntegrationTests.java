package poker.manager.api.integration.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import poker.manager.api.domain.Usuario;
import poker.manager.api.integration.config.TestConfig;
import poker.manager.api.integration.pojo.login.LoginFailureResponse;
import poker.manager.api.integration.pojo.login.LoginRequest;
import poker.manager.api.integration.pojo.login.LoginResponse;
import poker.manager.api.integration.pojo.usuario.UsuarioRequestResponse;
import poker.manager.api.integration.testcontainers.AbstractIntegrationTest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UsuarioIntegrationTests extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static Usuario usuario;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setContentType(ContentType.JSON)
                .setBasePath("/api/v1")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @DisplayName("Testanto Login efetuado com sucesso")
    void testLoginSuccessfuly() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("filipesag");
        loginRequest.setPassword("filipe123");
        LoginResponse loginResponse = given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .body(loginRequest)
                .when()
                .post("/auth/authenticate")
                .then()
                .statusCode(200)
                .extract().response().as(LoginResponse.class);

        String token = loginResponse.getToken();
        assertThat(token, matchesPattern("\\b[a-zA-Z0-9-_=]+\\.[a-zA-Z0-9-_=]+\\.[a-zA-Z0-9-_.+/=]+\\b"));
    }

    @Test
    @DisplayName("Testanto Login efetuado com falha por username errado")
    void testLoginFailedWithWrongUsername() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("filipes");
        loginRequest.setPassword("filipe123");
        LoginFailureResponse loginResponse = given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .body(loginRequest)
                .when()
                .post("/auth/authenticate")
                .then()
                .statusCode(403)
                .extract().response().as(LoginFailureResponse.class);

        assertThat(loginResponse.getTimeStamp(), matchesPattern("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]+)?([Zz]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?"));
        assertEquals(loginResponse.getStatus(), 403);
        assertEquals(loginResponse.getError(), "FORBIDDEN");
        assertEquals(loginResponse.getMessage(), "Ops! Credencial inválida. Por favor, verifique seu username e sua senha.");
        assertEquals(loginResponse.getPath(), "/api/v1/auth/authenticate");
    }

    @DisplayName("Testanto Login efetuado com falha por senha errada")
    void testLoginFailedWithWrongPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("filipesag");
        loginRequest.setPassword("filipe321");
        LoginFailureResponse loginResponse = given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .body(loginRequest)
                .when()
                .post("/auth/authenticate")
                .then()
                .statusCode(403)
                .extract().response().as(LoginFailureResponse.class);
        assertThat(loginResponse.getTimeStamp(), matchesPattern("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]+)?([Zz]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?"));
        assertEquals(loginResponse.getStatus(), 403);
        assertEquals(loginResponse.getError(), "FORBIDDEN");
        assertEquals(loginResponse.getMessage(), "Ops! Credencial inválida. Por favor, verifique seu username e sua senha.");
        assertEquals(loginResponse.getPath(), "/api/v1/auth/authenticate");
    }
}


