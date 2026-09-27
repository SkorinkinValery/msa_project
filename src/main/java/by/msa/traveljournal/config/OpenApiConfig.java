package by.msa.traveljournal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI travelJournalOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Журнал путешествий API")
                        .description("REST API для создания и ведения журнала путешествий")
                        .version("1.0"));
    }
}
