package poker.manager.api.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import poker.manager.api.service.DBService;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbService;

    @Bean
    public boolean instatiateDatabase() throws ParseException {
        dbService.instantiateTestDB();
        return true;
    }

}
