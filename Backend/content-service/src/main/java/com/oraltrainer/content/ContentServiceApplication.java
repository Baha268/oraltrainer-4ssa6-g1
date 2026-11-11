package com.oraltrainer.content;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Content Service API",
                version = "1.0.0",
                description = "Documentation OpenAPI du microservice Content Service pour la plateforme OralTrainer",
                contact = @Contact(
                        name = "OralTrainer Team",
                        email = "contact@oraltrainer.com"
                )
        )
)
@SpringBootApplication
public class ContentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentServiceApplication.class, args);
    }
}
