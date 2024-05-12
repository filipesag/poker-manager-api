package poker.manager.api.integration.testcontainers;

import org.hibernate.sql.results.graph.Initializer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{

        static PostgreSQLContainer<?> pgvector = new PostgreSQLContainer<>("pgvector/pgvector:pg16");

        private static void startContainers() {
            Startables.deepStart(Stream.of(pgvector)).join();
        }

        private static Map<String, String> createConnectionConfiguration() {
            return Map.of(
                    "spring.datasource.url",pgvector.getJdbcUrl(),
                    "spring.datasource.username",pgvector.getUsername(),
                    "spring.datasource.password",pgvector.getPassword()
            );
        }

        @Override
        @SuppressWarnings({"rawtypes", "unchecked"})
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment env = applicationContext.getEnvironment();
            MapPropertySource testContainers = new MapPropertySource("testcontainers", (Map) createConnectionConfiguration());
            env.getPropertySources().addFirst(testContainers);
        }
    }
}
