package com.oraltrainer.feedback.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration de la documentation Swagger / OpenAPI du microservice Feedback.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI feedbackServiceOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Feedback Service API")
                        .version("0.0.1")
                        .description("API du microservice Feedback de la plateforme OralTrainer : "
                                + "corrections grammaire / lexique / cohérence, recommandations "
                                + "et validation formateur.")
                        .contact(new Contact().name("Feriel Dridi — Étudiant 5")));

    }
}
