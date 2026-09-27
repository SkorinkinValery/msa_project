package by.msa.traveljournal.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldProvideOpenApiDescription() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("Журнал путешествий API"))
                .andExpect(jsonPath("$.paths['/api/trips'].get").exists())
                .andExpect(jsonPath("$.paths['/api/trips'].post").exists())
                .andExpect(jsonPath("$.paths['/api/trips/{id}'].put").exists())
                .andExpect(jsonPath("$.paths['/api/trips/{id}'].delete").exists())
                .andExpect(jsonPath("$.paths['/api/trips/{id}/guide'].get").exists())
                .andExpect(jsonPath("$.paths['/internal/destination-guides/{country}'].get").exists());
    }

    @Test
    void shouldRedirectToSwaggerUi() throws Exception {
        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/swagger-ui/index.html"));
    }
}
