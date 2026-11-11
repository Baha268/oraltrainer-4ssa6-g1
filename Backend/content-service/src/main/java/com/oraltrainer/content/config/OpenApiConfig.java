package com.oraltrainer.content.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI contentServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OralTrainer - Content Service API")
                        .version("1.0.0")
                        .description("""
                                Microservice **Content Service** de la plateforme **OralTrainer** (entraînement et évaluation de l'expression orale en français et en anglais, niveaux CECRL B1 à C2).
                                
                                ### Responsabilités principales :
                                - Gestion du catalogue des **Cours** (langues, niveaux CECRL)
                                - Gestion des **Modules** pédagogiques ordonnés
                                - Gestion des **Leçons** et contenus d'apprentissage
                                - Gestion des **Exercices** ciblés par thématiques d'erreur (Prononciation, Fluidité, Intonation, Vocabulaire, Cohérence)
                                """)
                        .contact(new Contact()
                                .name("Sherif Bahaeddine - Équipe OralTrainer")
                                .email("contact@oraltrainer.com"))
                        .license(new License()
                                .name("Projet Académique 4SSA6-G1")
                                .url("https://github.com/Baha268/oraltrainer-4ssa6-g1")))
                .servers(List.of(
                        new Server().url("http://localhost:8082").description("Serveur local Content Service (Port 8082)"),
                        new Server().url("http://localhost:8080").description("Via API Gateway (Port 8080)")
                ));
    }
}
