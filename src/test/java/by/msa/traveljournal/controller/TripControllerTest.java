package by.msa.traveljournal.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import by.msa.traveljournal.repository.TripRepository;

@SpringBootTest
@AutoConfigureMockMvc
class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TripRepository repository;

    @BeforeEach
    void clearDatabase() {
        repository.deleteAll();
    }

    @Test
    void shouldPerformCrudOperations() throws Exception {
        var createJson = """
                {
                  "title": "Летний отпуск",
                  "country": "Италия",
                  "city": "Рим",
                  "description": "Колизей",
                  "travelDate": "2026-07-15"
                }
                """;

        var createResult = mockMvc.perform(post("/api/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Летний отпуск"))
                .andExpect(jsonPath("$.city").value("Рим"))
                .andReturn();

        var id = repository.findAll().get(0).getId();
        assertThat(createResult.getResponse().getContentAsString()).contains(id.toString());

        mockMvc.perform(get("/api/trips/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country").value("Италия"));

        mockMvc.perform(get("/api/trips"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        mockMvc.perform(put("/api/trips/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"city\":\"Милан\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Милан"))
                .andExpect(jsonPath("$.country").value("Италия"));

        mockMvc.perform(delete("/api/trips/{id}", id))
                .andExpect(status().isNoContent());

        assertThat(repository.existsById(id)).isFalse();
    }

    @Test
    void shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/trips/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Путешествие не найдено: 999"));
    }

    @Test
    void shouldValidateCreateRequest() throws Exception {
        mockMvc.perform(post("/api/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
