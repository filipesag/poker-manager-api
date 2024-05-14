package poker.manager.api.integration.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import poker.manager.api.domain.Usuario;
import poker.manager.api.integration.config.TestConfig;
import poker.manager.api.integration.pojo.login.LoginRequest;
import poker.manager.api.integration.pojo.login.LoginResponse;
import poker.manager.api.integration.testcontainers.AbstractIntegrationTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseTests extends AbstractIntegrationTest {

    public static RequestSpecification getRequestSpecification() {
        RequestSpecification specification = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setContentType(ContentType.JSON)
                .setBasePath("/api/v1")
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
        return specification;
    }
    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }
    public static String login() {
        RequestSpecification specification = getRequestSpecification();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("filipesag");
        loginRequest.setPassword("filipe123");
        String token = given().spec(specification)
                .port(TestConfig.SERVER_PORT)
                .body(loginRequest)
                .when()
                .post("/auth/authenticate")
                .then()
                .statusCode(200)
                .extract().response().as(LoginResponse.class).getToken();
        assertThat(token, matchesPattern("\\b[a-zA-Z0-9-_=]+\\.[a-zA-Z0-9-_=]+\\.[a-zA-Z0-9-_.+/=]+\\b"));
        return token;
    }


}
