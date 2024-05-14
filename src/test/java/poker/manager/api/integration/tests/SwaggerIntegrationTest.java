package poker.manager.api.integration.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import poker.manager.api.integration.config.TestConfig;
import poker.manager.api.integration.testcontainers.AbstractIntegrationTest;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//class SwaggerIntegrationTest extends AbstractIntegrationTest {
//	private static RequestSpecification specification;
//	private static ObjectMapper objectMapper;
//	@BeforeAll
//	public static void setup() {
//		objectMapper = new ObjectMapper();
//		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//
//		specification = new RequestSpecBuilder()
//				.setBaseUri("http://localhost")
//				.setContentType(ContentType.JSON)
//				.setPort(TestConfig.SERVER_PORT)
//				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
//				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
//				.build();
//	}
//
//	@Test
//	@DisplayName("Deve abrir a página da documentação do Swagger")
//	void testShouldDisplaySwaggerUiPage() {
//		var content = given()
//			.basePath("/swagger-ui/index.html")
//				.when()
//				.get()
//				.then()
//				.statusCode(200)
//				.extract().body().asString();
//
//		assertTrue(content.contains("Swagger UI"));
//
//	}
//
//}
