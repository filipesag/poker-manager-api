package poker.manager.api.integration.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import poker.manager.api.integration.config.TestConfig;
import poker.manager.api.integration.testcontainers.AbstractIntegrationTest;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

	@Test
	@DisplayName("Deve abrir a página da documentação do Swagger")
	void testShouldDisplaySwaggerUiPage() {
		var content = given()
			.basePath("/swagger-ui/index.html")
			.port(TestConfig.SERVER_PORT)
				.when()
				.get()
				.then()
				.statusCode(200)
				.extract().body().asString();

		assertTrue(content.contains("Swagger UI"));

	}

}
