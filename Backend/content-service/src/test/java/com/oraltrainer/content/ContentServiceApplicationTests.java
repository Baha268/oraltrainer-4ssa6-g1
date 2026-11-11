package com.oraltrainer.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ContentServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldReturnHelloResponse() throws Exception {
        mockMvc.perform(get("/api/content/hello")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello from Content Microservice!"))
                .andExpect(jsonPath("$.service").value("content-service"))
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void shouldCreateAndRetrieveCourse() throws Exception {
        String courseJson = """
                {
                    "title": "Préparation DELF B2",
                    "description": "Entraînement complet à l'expression orale",
                    "language": "FRENCH",
                    "level": "B2"
                }
                """;

        mockMvc.perform(post("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Préparation DELF B2"))
                .andExpect(jsonPath("$.language").value("FRENCH"))
                .andExpect(jsonPath("$.level").value("B2"));

        mockMvc.perform(get("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
